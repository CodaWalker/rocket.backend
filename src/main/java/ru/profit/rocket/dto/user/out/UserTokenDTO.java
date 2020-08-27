package ru.profit.rocket.dto.user.out;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Полная ДТО для сущности "Данные для доступа"
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("Токен пользователя")
public class UserTokenDTO {
    @ApiModelProperty("UUID пользователя")
    private UUID id;
    @ApiModelProperty("Токен пользователя")
    private String token;
}
