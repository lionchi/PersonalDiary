package ru.jpixel.personaldiaryregistrationservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.jpixel.models.OperationResult;
import ru.jpixel.models.dtos.UserDto;

@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final UserServiceFeignClient userServiceFeignClient;

    public OperationResult registrationUser(UserDto userDto) {
        return userServiceFeignClient.save(userDto);
    }
}
