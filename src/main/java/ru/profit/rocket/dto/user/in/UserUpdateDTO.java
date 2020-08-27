package ru.profit.rocket.dto.user.in;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/** ДТО создания пользователя */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateDTO {
    @ApiModelProperty("Имя пользователя, логин")
    private String login;

    @ApiModelProperty("Пароль пользователя")
    private String password;

    @ApiModelProperty("Почта пользователя")
    private String email;
}