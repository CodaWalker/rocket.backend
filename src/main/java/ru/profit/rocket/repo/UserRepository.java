package ru.profit.rocket.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.profit.rocket.model.user.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    List<User> findByLogin(String name);
    Iterable<User> findAllByLoginContainingIgnoreCase(String phrase);
    void deleteById(UUID id);
    List<User> findByActivationCode(String code);
}
