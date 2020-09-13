package ru.jpixel.personaldiarymailservice.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.jpixel.models.OperationResult;
import ru.jpixel.models.dtos.PasswordResetTokenRequest;
import ru.jpixel.personaldiarymailservice.services.MailService;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class MailController {

    private final MailService mailService;

    @PostMapping("sendRecoveryPasswordMail")
    public OperationResult sendRecoveryPasswordMail(@RequestBody PasswordResetTokenRequest passwordResetTokenRequest) {
        return mailService.sendRecoveryPasswordMail(passwordResetTokenRequest);
    }
}
