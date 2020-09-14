package ru.jpixel.personaldiarymailservice.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.jpixel.models.OperationResult;
import ru.jpixel.models.dtos.PasswordResetTokenRequest;
import ru.jpixel.personaldiarymailservice.services.MailService;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class MailController {

    private final MailService mailService;

    @PostMapping("sendRecoveryPasswordMail")
    public OperationResult sendRecoveryPasswordMail(@RequestBody PasswordResetTokenRequest passwordResetTokenRequest,
                                                    @RequestParam("ln") String ln) {
        return mailService.sendRecoveryPasswordMail(passwordResetTokenRequest, ln);
    }
}
