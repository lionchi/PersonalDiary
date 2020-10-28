package ru.jpixel.models.dtos.secr;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RecoveryPasswordDto {
    @ApiModelProperty(notes = "Новый пароль")
    private String newPassword;
    @ApiModelProperty(notes = "Токен для восстановления пароля")
    private String token;
}
