package ru.jpixel.personaldiarymailservice.services;

import org.springframework.stereotype.Component;
import ru.jpixel.models.Error;
import ru.jpixel.models.OperationResult;
import ru.jpixel.models.dtos.PasswordResetTokenRequest;

@Component
public class UserServiceFallback implements UserServiceFeignClient {
    @Override
    public OperationResult createPasswordResetToken(PasswordResetTokenRequest passwordResetTokenRequest) {
        return new OperationResult(Error.UNAVAILABILITY_PASSWORD_RESET_SERVICE);
    }

    @Override
    public OperationResult findTokenByEmail(String email) {
        return new OperationResult(Error.UNAVAILABILITY_PASSWORD_RESET_SERVICE);
    }
}
