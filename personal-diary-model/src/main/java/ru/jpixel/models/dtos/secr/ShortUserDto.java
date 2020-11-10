package ru.jpixel.models.dtos.secr;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ShortUserDto {
    @ApiModelProperty(notes = "Имя пользователя")
    private String name;
    @ApiModelProperty(notes = "Email пользователя")
    private String email;
}
