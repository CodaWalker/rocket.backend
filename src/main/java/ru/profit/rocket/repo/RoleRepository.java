package ru.profit.rocket.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.profit.rocket.model.user.Role;
import ru.profit.rocket.model.user.User;

import java.util.Set;
import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {
    Role findByName(String name);
    Set<Role> getAllByUsersIn(Set<User> users);
}
