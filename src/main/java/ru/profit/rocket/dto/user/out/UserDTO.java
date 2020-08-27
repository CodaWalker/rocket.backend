package ru.profit.rocket.dto.user.out;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import ru.profit.rocket.dto.BaseDTO;

import java.util.Set;
import java.util.UUID;
/**
 * Полная ДТО для сущности "Пользователь"
 */
@EqualsAndHashCode(callSuper = true)
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("Пользователь")
public class UserDTO extends BaseDTO {

    @ApiModelProperty("Идентификатор пользователя")
    private UUID id;

    @ApiModelProperty("Имя пользователя, логин")
    private String login;

    @ApiModelProperty("Имя")
    private String firstName;

    @ApiModelProperty("Фамилия")
    private String lastName;

    @ApiModelProperty("Отчество")
    private String middleName;

    @JsonIgnore
    @ApiModelProperty("Пароль пользователя")
    private String password;

    @ApiModelProperty("Почта пользователя")
    private String email;

    @ApiModelProperty("Тип пользователя")
    private String typeUser;

    @ApiModelProperty("Код активации")
    private String activationCode;
}
