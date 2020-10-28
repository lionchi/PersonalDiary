package ru.jpixel.personaldiarymailservice.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.jpixel.models.dtos.common.OperationResult;
import ru.jpixel.models.dtos.secr.PasswordResetTokenDto;
import ru.jpixel.personaldiarymailservice.services.MailService;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
@Api(tags = "Email API")
public class MailController {

    private final MailService mailService;

    @PostMapping("sendRecoveryPasswordMail")
    @ApiOperation(value = "Отправить email для восстановления пароля", response = OperationResult.class)
    public OperationResult sendRecoveryPasswordMail(@RequestBody PasswordResetTokenDto passwordResetTokenDto,
                                                    @ApiParam(value = "Текущий язык клиента", allowableValues = "ru,en", required = true)
                                                    @RequestParam String ln) {
        return mailService.sendRecoveryPasswordMail(passwordResetTokenDto, ln);
    }
}
