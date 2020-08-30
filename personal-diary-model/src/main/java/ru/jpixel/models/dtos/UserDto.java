package ru.jpixel.models.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

public class UserDto {

    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String email;

    @Getter
    @Setter
    private String login;

    @Getter
    @Setter
    private String password;

    @Getter
    @Setter
    private String prefix;

    @Getter
    @Setter
    private String phone;

    @Getter
    @Setter
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    private LocalDate birthday;
}
