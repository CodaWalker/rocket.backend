package ru.profit.rocket.service.user.argument;

import lombok.Builder;
import lombok.Getter;
import ru.profit.rocket.error.UserError;
import ru.profit.rocket.util.Validator;

import java.time.LocalDateTime;

/** Аргумент создания пользователя */
@Getter
@Builder
public class UserCreateArgument {
    public UserCreateArgument(LocalDateTime creationDate, String login, String firstName, String lastName, String middleName, String password, String email, Integer role_id, String role_name, String typeUser) {
        this.role_id = role_id;
        this.role_name = role_name;
        Validator.validateObjectParam(login, UserError.USER_CREATE_ARGUMENT_LOGIN_IS_MANDATORY);
        Validator.validateObjectParam(password, UserError.USER_CREATE_ARGUMENT_PASSWORD_IS_MANDATORY);
        Validator.validateObjectParam(email, UserError.USER_CREATE_ARGUMENT_EMAIL_IS_MANDATORY);

        //        Validator.validateObjectParam(name, TicketError.TICKET_NAME_IS_MANDATORY);
//        Validator.validateObjectParam(description, TicketError.TICKET_DESCRIPTION_IS_MANDATORY);
        this.typeUser = typeUser;
        this.creationDate = creationDate;
        this.login = login;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.password = password;
        this.email = email;
    }

    private final LocalDateTime creationDate;
    private final String login;
    private final String firstName;
    private final String lastName;
    private final String middleName;
    private final String password;
    private final String email;
    private final Integer role_id;
    private final String role_name;
    private final String typeUser;
}
