package ru.profit.rocket.service.user;

import com.sun.istack.NotNull;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;
import ru.profit.rocket.config.jwt.JwtProvider;
import ru.profit.rocket.dto.user.out.UserAuthDTO;
import ru.profit.rocket.error.CommonError;
import ru.profit.rocket.model.search.SearchObject;
import ru.profit.rocket.model.user.Role;
import ru.profit.rocket.model.user.TypeUser;
import ru.profit.rocket.model.user.User;
import ru.profit.rocket.repo.UserRepository;
import ru.profit.rocket.service.MailSender;
import ru.profit.rocket.service.role.RoleService;
import ru.profit.rocket.service.user.argument.UserAuthorizationArgument;
import ru.profit.rocket.service.user.argument.UserCreateArgument;
import ru.profit.rocket.service.user.argument.UserSearchArgument;
import ru.profit.rocket.service.user.argument.UserUpdateArgument;
import ru.profit.rocket.util.Validator;
import ru.profit.rocket.util.logger.Logging;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class UserServiceImpl implements UserService{
    @Logging()
    public static Logger LOG;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final RoleService roleService;
    private final MailSender mailSender;

    @Value("${server.hostnameFull}")
    private String SHostname;

    @Value("${client.hostnameFull}")
    private String CHostname;

    @Autowired
    public UserServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepository, JwtProvider jwtProvider, RoleService roleService, MailSender mailSender) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
        this.roleService = roleService;
        this.mailSender = mailSender;
    }

    public String getToken(UserAuthDTO dto){
        return jwtProvider.generateToken(findByLoginAndPassword(
                UserAuthorizationArgument.builder()
                        .login(dto.getLogin())
                        .password(dto.getPassword())
                        .build()
        ).getLogin());
    }

    @Transactional
    @Override
    public User create(UserCreateArgument userCreateArgument) throws Exception {
        Role role = roleService.findByName(Role.Default.ROLE_ADMIN.name());
        Set<Role> roles = new HashSet<>();
        roles.add(role);

        User user = userRepository.save(User.builder()
                .login(userCreateArgument.getLogin())
                .password(new BCryptPasswordEncoder().encode(userCreateArgument.getPassword()))
                .email(userCreateArgument.getEmail())
                .typeUser(TypeUser.SPECIALIST.name()) //hardcode
                .roles(roles)
                .build());
        sendMessageUserActivate(user);
        return user;

    }

    @Transactional
    @Override
    public User update(UUID id, UserUpdateArgument userUpdateArgument){ //3 field updated, other no updated
        User updatingUser = userRepository.getOne(id);
        updatingUser.setLogin(userUpdateArgument.getLogin());
        updatingUser.setPassword(new BCryptPasswordEncoder().encode(userUpdateArgument.getPassword()));
        updatingUser.setAccepted(updatingUser.isAccepted());
        updatingUser.setLocked(updatingUser.isAccepted());
        updatingUser.setEmail(userUpdateArgument.getEmail());
        updatingUser.setTypeUser(updatingUser.getTypeUser());
        updatingUser.setRoles(updatingUser.getRoles());
        return userRepository.save(updatingUser);
    }

    @Transactional
    @Override
    public boolean delete(UUID id){
        userRepository.deleteById(id);
        return exists(id);
    }

    @Transactional
    public boolean exists(UUID id) {
        return userRepository.findById(id).isPresent();
    }


    @Transactional
    public User findByLogin(String login) {
        List<User> users = userRepository.findByLogin(login);
        AtomicReference<User> user = new AtomicReference<>(new User());
            users.forEach(u->{
                User tmp = new User();
                if(users.size() == 1) {
                    tmp = u;
                }
                user.set(tmp);
            });
            return user.get();
    }

    @Override
    @NotNull
    public Page<User> getAll(Pageable pageable) {
        Validator.validateObjectParam(pageable, CommonError.PAGEABLE_IS_MANDATORY);
        return userRepository.findAll(pageable);
    }

    @Override
    public User getOne(UUID id) {
        Optional<User> byId = userRepository.findById(id);
        return byId.orElseGet(() -> byId.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")));
    }

    @Override
    @Transactional(readOnly = true)
    public User findByLoginAndPassword(UserAuthorizationArgument userAuthorizationArgument) {
        User userEntity = findByLogin(userAuthorizationArgument.getLogin());
        if (passwordEncoder.matches(userAuthorizationArgument.getPassword(), userEntity.getPassword())) {
            return userEntity;
        }
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<User> getAllByParam(UserSearchArgument searchArgument, Pageable pageable) {
//        if(searchArgument.getLogin()!=null){
        String value = searchArgument.getLogin().getValue();
        return userRepository.findAllByLoginContainingIgnoreCase(value);
//        }
//        return userRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findByActivationCode(String code) {
        return userRepository.findByActivationCode(code);
    }
    public boolean isEmail(String email){
        final UserSearchArgument userSearchArgument = UserSearchArgument.builder().email(getSearchEqualsObject(email)).build();

        return false;
    }

    private SearchObject<String> getSearchEqualsObject(String value) {
        return SearchObject.<String>builder().value(value).type(SearchObject.Type.EQUALS).build();
    }
    private SearchObject<Boolean> getSearchEqualsObject(Boolean value) {
        return SearchObject.<Boolean>builder().value(value).type(SearchObject.Type.EQUALS).build();
    }

    public boolean activateUser(String code) {
        List<User> users = findByActivationCode(code);
        if(users.size() == 1) {
            final User user = users.get(0);
            if (user == null) {
                return false;
            }
            if(!user.isAccepted()) {
                user.setAccepted(true);
                try {
                    sendMessUserActivateSuccess(user);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                userRepository.save(user);
            }else{
                LOG.info("Учетная запись уже подтверждена!");;
                return true;
            }
            LOG.info("Учетная запись подтверждена!");;
            return true;
        }else if(users.size() > 1){
            LOG.error("По непонятным причинам были созданы дублирующие активационные коды!");
            return false;
        }
        LOG.warn("Данного кода нет в базе!");
        return false;
    }

    public boolean userLock(UUID id) {
        User user = userRepository.getOne(id);
        if(user!=null) {
            user.setLocked(true);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    public boolean userUnlock(UUID id) {
        User user = userRepository.getOne(id);
        if(user!=null) {
            user.setLocked(false);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    public boolean userAccept(UUID id) {
        User user = userRepository.getOne(id);
        if(user!=null) {
            user.setAccepted(true);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    private void sendMessageUserActivate(User user) throws Exception {
        if (!StringUtils.isEmpty(user.getEmail())) {
            String message = String.format(
                    "Добро пожаловать!, %s! \n"  +
                            ".Для активации учетной записи в SD Solution, перейдтие по этой ссылке: http://%s/api/service/activate/%s",
                    user.getLogin(),
                    SHostname,
                    Objects.requireNonNull(userRepository.getOne(user.getId())).getActivationCode()
            );

            mailSender.send(user.getEmail(), "Activation code", message);

        }
    }
    private void sendMessUserActivateSuccess(User user) throws Exception {
        if (!StringUtils.isEmpty(user.getEmail())) {
//             user.setRoles(Collections.singleton(Role.ADMIN));

            String message = String.format(
                    "Поздравляем!, %s! \n" +
                            ".Ваша учетная запись SD Solution активирована, перейдтие по ссылке: http://%s для входа",
                    user.getLogin(),
                    CHostname
            );
            mailSender.send(user.getEmail(), "Success activation code", message);
        }
    }


}
