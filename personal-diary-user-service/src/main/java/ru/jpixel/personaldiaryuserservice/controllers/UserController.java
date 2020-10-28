package ru.jpixel.personaldiaryuserservice.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.jpixel.models.dtos.common.OperationResult;
import ru.jpixel.models.dtos.secr.PasswordResetTokenDto;
import ru.jpixel.models.dtos.secr.RecoveryPasswordDto;
import ru.jpixel.models.dtos.secr.UserDto;
import ru.jpixel.personaldiaryuserservice.services.UserService;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
@Api(tags = "User API")
public class UserController {

    private final UserService userService;

    @PostMapping("save")
    @ApiOperation(value = "Сохранить нового пользователя", response = OperationResult.class)
    public OperationResult save(@RequestBody UserDto userDto) {
        return userService.save(userDto);
    }

    @GetMapping("findByLogin/{login}")
    @ApiOperation(value = "Найти пользователя по его логину", response = OperationResult.class, authorizations = {@Authorization(value = "header")})
    public UserDto findByLogin(@PathVariable String login) {
        return userService.findByLogin(login);
    }

    @PostMapping("create/passwordResetToken")
    @ApiOperation(value = "Создать запись с новым токеном для восстановления пароля", response = OperationResult.class, authorizations = {@Authorization(value = "header")})
    public OperationResult createPasswordResetToken(@RequestBody PasswordResetTokenDto passwordResetTokenDto) {
        return userService.createPasswordResetToken(passwordResetTokenDto);
    }

    @PutMapping("recoveryPassword")
    @ApiOperation(value = "Восстановить пароль", response = OperationResult.class, authorizations = {@Authorization(value = "header")})
    public OperationResult recoveryPassword(@RequestBody RecoveryPasswordDto recoveryPasswordDto) {
        return userService.recoveryPassword(recoveryPasswordDto);
    }

    @GetMapping("findTokenByEmail/{email}")
    @ApiOperation(value = "Найти токен восстановления по email пользователя", response = OperationResult.class, authorizations = {@Authorization(value = "header")})
    public OperationResult findTokenByEmail(@PathVariable String email) {
        return userService.findTokenByEmail(email);
    }
}
