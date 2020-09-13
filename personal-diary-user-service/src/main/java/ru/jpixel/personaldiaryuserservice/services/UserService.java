package ru.jpixel.personaldiaryuserservice.services;

import com.fasterxml.jackson.annotation.JsonIgnoreType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.jpixel.models.Error;
import ru.jpixel.models.OperationResult;
import ru.jpixel.models.Success;
import ru.jpixel.models.dtos.PasswordResetTokenRequest;
import ru.jpixel.models.dtos.RecoveryPasswordDto;
import ru.jpixel.models.dtos.UserDto;
import ru.jpixel.models.dtos.UserResetTokenDto;
import ru.jpixel.personaldiaryuserservice.domain.PasswordResetToken;
import ru.jpixel.personaldiaryuserservice.domain.Role;
import ru.jpixel.personaldiaryuserservice.domain.User;
import ru.jpixel.personaldiaryuserservice.repositories.PasswordResetTokenRepository;
import ru.jpixel.personaldiaryuserservice.repositories.RoleRepository;
import ru.jpixel.personaldiaryuserservice.repositories.UserRepository;

import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.Collections;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public OperationResult save(UserDto userDto) {
        if (userRepository.existsByLogin(userDto.getLogin())) {
            return new OperationResult(Error.LOGIN_EXIST, userDto.getLogin());
        } else if (userRepository.existsByEmail(userDto.getEmail())) {
            return new OperationResult(Error.EMAIL_EXIST);
        }
        var user = new User();
        user.setName(userDto.getName());
        user.setLogin(userDto.getLogin());
        user.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        user.setEmail(userDto.getEmail());
        if (StringUtils.isNotEmpty(userDto.getPhone())) {
            user.setPrefix(userDto.getPrefix());
            user.setPhone(userDto.getPhone());
        }
        user.setBirthday(userDto.getBirthday());
        user.setRoles(Collections.singletonList(roleRepository.findByName("USER")));
        userRepository.save(user);
        return new OperationResult(Success.REGISTRATION);
    }

    public UserDto findByLogin(String login) {
        var foundUser = userRepository.findByLogin(login);
        if (foundUser == null) {
            return null;
        }
        var userDto = new UserDto();
        userDto.setId(foundUser.getId());
        userDto.setBirthday(foundUser.getBirthday());
        userDto.setPassword(foundUser.getPassword());
        userDto.setEmail(foundUser.getEmail());
        userDto.setPrefix(foundUser.getPrefix());
        userDto.setPhone(foundUser.getPhone());
        userDto.setPrefix(foundUser.getPrefix());
        userDto.setLogin(foundUser.getLogin());
        userDto.setRoles(foundUser.getRoles().stream().map(Role::getName).collect(Collectors.toList()));
        return userDto;
    }

    @Transactional
    public OperationResult createPasswordResetToken(PasswordResetTokenRequest passwordResetTokenRequest) {
        if (!userRepository.existsByEmail(passwordResetTokenRequest.getUserEmail())) {
            return new OperationResult(Error.EMAIL_NOT_EXIST);
        } else if (passwordResetTokenRepository.findByUserEmail(passwordResetTokenRequest.getUserEmail()) != null) {
            return new OperationResult(Error.PASSWORD_RESET_TOKEN_NOT_UNIQUE);
        }
        var passwordResetToken = new PasswordResetToken();
        passwordResetToken.setToken(UUID.randomUUID().toString());
        passwordResetToken.setExpiryDate(LocalDate.now().plusDays(3));
        passwordResetToken.setUser(userRepository.findByEmail(passwordResetTokenRequest.getUserEmail()));
        passwordResetTokenRepository.save(passwordResetToken);
        return new OperationResult(Success.PASSWORD_RESET_TOKEN_CREATE);
    }

    @Transactional
    public OperationResult recoveryPassword(RecoveryPasswordDto recoveryPasswordDto) {
        var findToken = passwordResetTokenRepository.findByToken(recoveryPasswordDto.getToken());
        if (findToken == null) {
            return new OperationResult(Error.PASSWORD_RESET_TOKEN_NOT_EXIST);
        } else if (findToken.isExpired()) {
            passwordResetTokenRepository.delete(findToken);
            return new OperationResult(Error.PASSWORD_RESET_TOKEN_EXPIRED);
        }
        var user = findToken.getUser();
        var encodeNewPassword = bCryptPasswordEncoder.encode(recoveryPasswordDto.getNewPassword());
        userRepository.updatePassword(encodeNewPassword, user.getId());
        passwordResetTokenRepository.delete(findToken);
        return new OperationResult(Success.RECOVERY_PASSWORD);
    }

    public OperationResult findTokenByEmail(String email) {
        var passwordResetToken = passwordResetTokenRepository.findByUserEmail(email);
        if (passwordResetToken == null) {
            return new OperationResult(Error.PASSWORD_RESET_TOKEN_NOT_EXIST);
        }
        var userResetTokenDto = new UserResetTokenDto();
        userResetTokenDto.setName(passwordResetToken.getUser().getName());
        userResetTokenDto.setToken(passwordResetToken.getToken());
        var successOperationResult = new OperationResult(Success.BASE_OPERATION);
        try {
            ObjectWriter objectWriter = new ObjectMapper().addMixIn(PrintWriter.class, JsonIgnoreType.class).writer();
            successOperationResult.setJson(objectWriter.writeValueAsString(userResetTokenDto));
        } catch (JsonProcessingException e) {
            return new OperationResult(Error.BASE_OPERATION);
        }
        return successOperationResult;
    }

}
