package ru.jpixel.models.dtos.secr;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UserDto {
    @ApiModelProperty(notes = "Идентификатор пользователя")
    private Long id;
    @ApiModelProperty(notes = "Имя пользователя")
    private String name;
    @ApiModelProperty(notes = "Email пользователя")
    private String email;
    @ApiModelProperty(notes = "Логин пользователя")
    private String login;
    @ApiModelProperty(notes = "Пароль пользователя")
    private String password;
    @ApiModelProperty(notes = "Код страны")
    private String prefix;
    @ApiModelProperty(notes = "Номер телефона без учета кода страны")
    private String phone;
    @ApiModelProperty(notes = "Дата рождения")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    private LocalDate birthday;
    @ApiModelProperty(notes = "Список ролей")
    private List<String> roles = new ArrayList<>();
}
