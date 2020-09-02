package ru.jpixel.personaldiaryclient.web.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.jpixel.personaldiaryclient.web.security.PersonalDiaryUser;

@RestController
@RequestMapping("/api/account")
public class AccountController {

    @GetMapping("information")
    public PersonalDiaryUser information(@AuthenticationPrincipal PersonalDiaryUser user) {
        return user;
    }
}
