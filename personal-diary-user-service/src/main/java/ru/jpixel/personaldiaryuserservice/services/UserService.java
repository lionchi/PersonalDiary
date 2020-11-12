package ru.jpixel.personaldiaryuserservice.services;

import ru.jpixel.models.dtos.common.OperationResult;
import ru.jpixel.models.dtos.secr.PasswordResetTokenDto;
import ru.jpixel.models.dtos.secr.RecoveryPasswordDto;
import ru.jpixel.models.dtos.secr.ShortUserDto;
import ru.jpixel.models.dtos.secr.UserDto;

import java.util.List;

public interface UserService {
    OperationResult save(UserDto userDto);

    OperationResult update(UserDto userDto);

    UserDto findByLogin(String login);

    OperationResult createPasswordResetToken(PasswordResetTokenDto passwordResetTokenDto);

    OperationResult recoveryPassword(RecoveryPasswordDto recoveryPasswordDto);

    OperationResult findTokenByEmail(String email);

    List<ShortUserDto> searchForUserToNotify();
}
