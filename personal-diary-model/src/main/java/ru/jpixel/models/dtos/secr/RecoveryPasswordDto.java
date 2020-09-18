package ru.jpixel.models.dtos.secr;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RecoveryPasswordDto {
    private String newPassword;
    private String token;
}
