package ru.jpixel.personaldiaryuserservice.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.jpixel.models.OperationResult;
import ru.jpixel.models.dtos.PasswordResetTokenRequest;
import ru.jpixel.models.dtos.RecoveryPasswordDto;
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

    @GetMapping("findByLogin/{login}")
    public UserDto findByLogin(@PathVariable String login) {
        return userService.findByLogin(login);
    }

    @PostMapping("create/passwordResetToken")
    public OperationResult createPasswordResetToken(@RequestBody PasswordResetTokenRequest passwordResetTokenRequest) {
        return userService.createPasswordResetToken(passwordResetTokenRequest);
    }

    @PutMapping("recoveryPassword")
    public OperationResult recoveryPassword(@RequestBody RecoveryPasswordDto recoveryPasswordDto) {
        return userService.recoveryPassword(recoveryPasswordDto);
    }

    @GetMapping("findTokenByEmail/{email}")
    public OperationResult findTokenByEmail(@PathVariable String email) {
        return userService.findTokenByEmail(email);
    }
}
