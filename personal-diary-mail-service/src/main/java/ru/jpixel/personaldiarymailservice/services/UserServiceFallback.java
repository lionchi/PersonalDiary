package ru.jpixel.personaldiarymailservice.services;

import org.springframework.stereotype.Component;
import ru.jpixel.models.dtos.common.Error;
import ru.jpixel.models.dtos.common.OperationResult;
import ru.jpixel.models.dtos.secr.PasswordResetTokenDto;

@Component
public class UserServiceFallback implements UserServiceFeignClient {
    @Override
    public OperationResult createPasswordResetToken(PasswordResetTokenDto passwordResetTokenDto) {
        return new OperationResult(Error.UNAVAILABILITY_PASSWORD_RESET_SERVICE);
    }

    @Override
    public OperationResult findTokenByEmail(String email) {
        return new OperationResult(Error.UNAVAILABILITY_PASSWORD_RESET_SERVICE);
    }
}
