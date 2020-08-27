package ru.profit.rocket.service.user.argument;

import lombok.Builder;
import lombok.Getter;
import ru.profit.rocket.model.search.SearchObject;
import ru.profit.rocket.model.user.Role;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

/** Аргумент поиска пользователя по параметрам */
@Getter
@Builder
public class UserSearchArgument {

    private final SearchObject<UUID> id;
    private final SearchObject<LocalDateTime> creationDate;
    private final SearchObject<String> login;
    private final SearchObject<String> firstName;
    private final SearchObject<String> lastName;
    private final SearchObject<String> middleName;
    private final SearchObject<String> password;
    private final SearchObject<String> email;
    private final SearchObject<Set<Role>> roles;
    private final SearchObject<String> typeUser;
    private final SearchObject<Boolean> locked;
    private final SearchObject<Boolean> accepted;
    private final SearchObject<String> specialization;
    private final SearchObject<String> activationCode;
}
