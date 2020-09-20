package ru.jpixel.personaldiarymailservice.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.jpixel.models.dtos.common.OperationResult;
import ru.jpixel.models.dtos.secr.PasswordResetTokenDto;
import ru.jpixel.personaldiarymailservice.services.MailService;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class MailController {

    private final MailService mailService;

    @PostMapping("sendRecoveryPasswordMail")
    public OperationResult sendRecoveryPasswordMail(@RequestBody PasswordResetTokenDto passwordResetTokenDto, @RequestParam String ln) {
        return mailService.sendRecoveryPasswordMail(passwordResetTokenDto, ln);
    }
}
