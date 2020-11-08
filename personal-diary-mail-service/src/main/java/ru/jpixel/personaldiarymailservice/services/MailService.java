package ru.jpixel.personaldiarymailservice.services;

import ru.jpixel.models.dtos.common.OperationResult;
import ru.jpixel.models.dtos.secr.PasswordResetTokenDto;

public interface MailService {
    OperationResult sendRecoveryPasswordMail(PasswordResetTokenDto passwordResetTokenDto, String ln);
}
