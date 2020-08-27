package ru.profit.rocket.service.role;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.profit.rocket.model.user.Role;
import ru.profit.rocket.model.user.User;
import ru.profit.rocket.repo.RoleRepository;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository userRoleRepository;

    public RoleServiceImpl(RoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public Role findByName(String name) {
        return userRoleRepository.findByName(name);
    }

    @Override
    public boolean isUserRole(String searchUser, String searchRole) {
        List<Role.Default> roles = Arrays.asList(Role.Default.values());
        Role.RoleBuilder roleBuilder = Role.builder().name(null);
        AtomicReference<String> searchSuccessName = new AtomicReference<>("");
        AtomicBoolean searchSuccessRole = new AtomicBoolean(false);
        roles.forEach(r->{
            String name = r.name();
            if(name.toLowerCase().contains(searchRole.toLowerCase())){
                searchSuccessName.set(name);
            }
        });
        if(!searchSuccessName.get().equals("")) {
            roleBuilder.name(searchSuccessName.get());
            Role role = roleBuilder.build();
            getAllByParam(role, PageRequest.of(0,Integer.MAX_VALUE)).forEach(r->{
                r.getUsers().forEach(u->{
                    if(u.getLogin().equals(searchUser)){
                        searchSuccessRole.set(true);
                    }
                });
            });
        }
        return searchSuccessRole.get();
    }

    @Transactional(readOnly = true)
    public Page<Role> getAllByParam(Role searchArgument, Pageable pageable) {
        return null;
    }

    @Transactional
    public Optional<Role> exists(UUID id){
        return userRoleRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Set<Role> getAllRolesByUsers(Set<User> users){
        return userRoleRepository.getAllByUsersIn(users);
    }

    @Transactional(readOnly = true)
    public Set<Role> getAllRolesByUser(User user){
        Set<User> users = new HashSet<>();
        users.add(user);
        return userRoleRepository.getAllByUsersIn(users);
    }
}
