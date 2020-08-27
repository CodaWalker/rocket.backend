package ru.profit.rocket.service.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.profit.rocket.dto.user.out.UserAuthDTO;
import ru.profit.rocket.model.BaseEntity;
import ru.profit.rocket.model.user.User;
import ru.profit.rocket.service.user.argument.UserAuthorizationArgument;
import ru.profit.rocket.service.user.argument.UserCreateArgument;
import ru.profit.rocket.service.user.argument.UserSearchArgument;
import ru.profit.rocket.service.user.argument.UserUpdateArgument;

import java.util.List;
import java.util.UUID;

@Service
public interface UserService {
    String getToken(UserAuthDTO dto);
    User create(UserCreateArgument userCreateArgument) throws Exception;
    User update(UUID id, UserUpdateArgument userUpdateArgument);
    boolean delete(UUID id);
    User findByLoginAndPassword(UserAuthorizationArgument userAuthorizationArgument);
    User findByLogin(String login);
    //Page<User> getAll(String pageNo, String pageSize, String sortField, boolean descending);
    Page<User> getAll(Pageable pageable);
    User getOne(UUID id) throws Exception;
    Iterable<User> getAllByParam(UserSearchArgument searchArgument, Pageable pageable);
    boolean exists(UUID id);
    boolean activateUser(String activateCode);
    List<User> findByActivationCode(String code);
    boolean isEmail(String email);
}
