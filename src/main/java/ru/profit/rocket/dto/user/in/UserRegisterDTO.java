package ru.profit.rocket.dto.user.in;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.time.LocalDateTime;

/** ДТО создания пользователя */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterDTO {
    @ApiModelProperty("Имя пользователя, логин")
    private String login;

    @ApiModelProperty("Пароль пользователя")
    private String password;

    @ApiModelProperty("Почта пользователя")
    private String email;
}