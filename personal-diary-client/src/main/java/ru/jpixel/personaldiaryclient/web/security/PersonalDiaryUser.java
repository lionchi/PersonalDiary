package ru.jpixel.personaldiaryclient.web.security;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@RequiredArgsConstructor
@Getter
@Setter
public class PersonalDiaryUser {
    @ApiModelProperty(notes = "Идентификатор текущего пользователя")
    @ApiParam(value = "Идентификатор текущего пользователя")
    private final Long id;
    @ApiModelProperty(notes = "Идентификатор дневника текущего пользователя")
    @ApiParam(value = "Идентификатор дневника текущего пользователя")
    private final Long diaryId;
    @ApiModelProperty(notes = "Логин текущего пользователя")
    @ApiParam(value = "Логин текущего пользователя")
    private final String username;
    @ApiModelProperty(notes = "Роли текущего пользователя")
    @ApiParam(value = "Роли текущего пользователя")
    private final List<String> roles;
}
