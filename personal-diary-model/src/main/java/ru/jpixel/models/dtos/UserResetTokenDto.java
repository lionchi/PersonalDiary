package ru.jpixel.models.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserResetTokenDto {
    private String name;
    private String token;
}
