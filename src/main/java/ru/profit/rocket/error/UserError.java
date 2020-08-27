package ru.profit.rocket.error;

/** Ошибки пользователя */
public class UserError {

    public static final String USER_NAME_IS_MANDATORY = "Имя пользователя является обязательным и не может быть пустым";
    public static final String USER_DESCRIPTION_IS_MANDATORY = "Описание пользователя является обязательным аргументом";
    public static final String USER_STATUS_IS_MANDATORY = "Статус пользователя является обязательным аргументом";
    public static final String USER_CREATE_ARGUMENT_LOGIN_IS_MANDATORY = "Логин является обязательным аргументом";
    public static final String USER_CREATE_ARGUMENT_PASSWORD_IS_MANDATORY = "Пароль является обязательным аргументом";
    public static final String USER_CREATE_ARGUMENT_EMAIL_IS_MANDATORY = "Электронная почта  является обязательным аргументом";
    public static final String USER_CREATE_ARGUMENT_LOGIN_IS_EXISTS = "Такой логин уже присутствует";
    public static final String USER_UPDATE_ARGUMENT_IS_MANDATORY = "Аргумент обновления пользователя является обязательным аргументом";
    public static final String USER_ID_IS_MANDATORY = "Индентификатор пользователя является обязательным аргументом";
    public static final String USER_NOT_FOUND = "Пользователь не найдена";
}
