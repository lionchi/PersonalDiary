package ru.jpixel.personaldiaryclient.web.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.jpixel.personaldiaryclient.web.security.PersonalDiaryUser;

@RestController
@RequestMapping("/api/account")
@Api(tags = "User information API")
public class CurrentUserInformationController {

    @GetMapping("information")
    @ApiOperation(value = "Получить информацию по авторизованному пользователю", response = PersonalDiaryUser.class)
    public PersonalDiaryUser information(@AuthenticationPrincipal PersonalDiaryUser user) {
        return user;
    }
}
