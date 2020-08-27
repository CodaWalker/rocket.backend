package ru.profit.rocket.service.role;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.profit.rocket.model.user.Role;
import ru.profit.rocket.model.user.User;

import java.util.List;
import java.util.Set;

public interface RoleService {
    Role findByName(String name);
    Page<Role> getAllByParam(Role searchArgument, Pageable pageable);
    boolean isUserRole(String searchUser, String searchRole);
    Set<Role> getAllRolesByUser(User user);
    Set<Role> getAllRolesByUsers(Set<User> users);
}
