package ru.profit.rocket.service.user.argument;

import lombok.Builder;
import lombok.Getter;
import ru.profit.rocket.error.UserError;
import ru.profit.rocket.util.Validator;

/** Аргумент авторизации пользователя */
@Getter
@Builder
public class UserAuthorizationArgument {
    public UserAuthorizationArgument(String login, String password) {
        Validator.validateObjectParam(login, UserError.USER_CREATE_ARGUMENT_LOGIN_IS_MANDATORY);
        Validator.validateObjectParam(password, UserError.USER_CREATE_ARGUMENT_PASSWORD_IS_MANDATORY);

        this.login = login;
        this.password = password;
    }

    private final String login;
    private final String password;
}
