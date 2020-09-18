package ru.jpixel.personaldiaryregistrationservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.jpixel.models.dtos.common.OperationResult;
import ru.jpixel.models.dtos.secr.UserDto;

@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final UserServiceFeignClient userServiceFeignClient;

    public OperationResult registrationUser(UserDto userDto) {
        return userServiceFeignClient.save(userDto);
    }
}
