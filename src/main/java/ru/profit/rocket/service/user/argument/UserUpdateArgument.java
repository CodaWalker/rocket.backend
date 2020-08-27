package ru.profit.rocket.service.user.argument;

import lombok.Builder;
import lombok.Getter;
import ru.profit.rocket.model.user.Role;

import java.time.LocalDateTime;
import java.util.Set;

/** Аргумент обновления пользователя */
@Getter
@Builder
public class UserUpdateArgument {
//        Validator.validateObjectParam(userId, TicketError.TICKET_USER_ID_IS_MANDATORY);
//        Validator.validateObjectParam(name, TicketError.TICKET_NAME_IS_MANDATORY);
//        Validator.validateObjectParam(description, TicketError.TICKET_DESCRIPTION_IS_MANDATORY);
//        Validator.validateObjectParam(status, TicketError.TICKET_DESCRIPTION_IS_MANDATORY);
    public UserUpdateArgument(LocalDateTime creationDate, String login, String firstName, String lastName, String middleName, String password, String email, Boolean locked, Boolean accepted, Set<Role> roles, String typeUser, String specialization) {
        this.creationDate = creationDate;
        this.login = login;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.password = password;
        this.email = email;
        this.locked = locked;
        this.accepted = accepted;
        this.roles = roles;
        this.typeUser = typeUser;
        this.specialization = specialization;
    }

    private final LocalDateTime creationDate;
    private final String login;
    private final String firstName;
    private final String lastName;
    private final String middleName;
    private final String password;
    private final String email;
    private final Boolean locked;
    private final Boolean accepted;
    private final Set<Role> roles;
    private final String typeUser;
    private final String specialization;

}
