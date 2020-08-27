package ru.profit.rocket.dto.user.out;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserAuthDTO {
    @ApiModelProperty("Имя пользователя, логин")
    private String login;

    @ApiModelProperty("Пароль пользователя")
    private String password;
}
