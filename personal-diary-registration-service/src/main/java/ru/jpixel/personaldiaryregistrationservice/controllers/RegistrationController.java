package ru.jpixel.personaldiaryregistrationservice.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.jpixel.models.OperationResult;
import ru.jpixel.models.dtos.UserDto;
import ru.jpixel.personaldiaryregistrationservice.services.RegistrationService;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping("registration")
    public OperationResult registrationUser(@RequestBody UserDto userDto) {
        return registrationService.registrationUser(userDto);
    }
}
