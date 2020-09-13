package ru.jpixel.personaldiaryregistrationservice.services;

import org.springframework.stereotype.Component;
import ru.jpixel.models.Error;
import ru.jpixel.models.OperationResult;
import ru.jpixel.models.dtos.UserDto;

@Component
public class UserServiceFallback implements UserServiceFeignClient {
    @Override
    public OperationResult save(UserDto userDto) {
        return new OperationResult(Error.UNAVAILABILITY_REGISTRATION_SERVICE);
    }
}
