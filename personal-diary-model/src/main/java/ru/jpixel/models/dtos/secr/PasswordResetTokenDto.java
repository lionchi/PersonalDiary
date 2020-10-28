package ru.jpixel.models.dtos.secr;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PasswordResetTokenDto {
    @ApiModelProperty(notes = "Электронная почта пользователя")
    private String userEmail;
}
