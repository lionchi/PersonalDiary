package ru.jpixel.personaldiaryuserservice.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.jpixel.models.OperationResult;
import ru.jpixel.models.dtos.UserDto;
import ru.jpixel.personaldiaryuserservice.services.UserService;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("save")
    public OperationResult save(@RequestBody UserDto userDto) {
        return userService.save(userDto);
    }
}
