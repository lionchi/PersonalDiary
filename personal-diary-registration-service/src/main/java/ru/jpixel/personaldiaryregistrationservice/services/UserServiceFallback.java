package ru.jpixel.personaldiaryregistrationservice.services;

import org.springframework.stereotype.Component;
import ru.jpixel.models.dtos.common.Error;
import ru.jpixel.models.dtos.common.OperationResult;
import ru.jpixel.models.dtos.secr.UserDto;

@Component
public class UserServiceFallback implements UserServiceFeignClient {
    @Override
    public OperationResult save(UserDto userDto) {
        return new OperationResult(Error.UNAVAILABILITY_REGISTRATION_SERVICE);
    }
}
