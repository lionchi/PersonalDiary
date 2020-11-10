package ru.jpixel.personaldiaryuserservice.services;

import com.fasterxml.jackson.annotation.JsonIgnoreType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.jpixel.models.dtos.common.Error;
import ru.jpixel.models.dtos.common.OperationResult;
import ru.jpixel.models.dtos.common.Success;
import ru.jpixel.models.dtos.secr.*;
import ru.jpixel.personaldiaryuserservice.domain.secr.PasswordResetToken;
import ru.jpixel.personaldiaryuserservice.domain.secr.Role;
import ru.jpixel.personaldiaryuserservice.domain.secr.User;
import ru.jpixel.personaldiaryuserservice.repositories.secr.PasswordResetTokenRepository;
import ru.jpixel.personaldiaryuserservice.repositories.secr.RoleRepository;
import ru.jpixel.personaldiaryuserservice.repositories.secr.UserRepository;

import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final DiaryServiceFeignClient diaryServiceFeignClient;

    @Override
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

    @Override
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

    @Override
    @Transactional
    public OperationResult createPasswordResetToken(PasswordResetTokenDto passwordResetTokenDto) {
        if (!userRepository.existsByEmail(passwordResetTokenDto.getUserEmail())) {
            return new OperationResult(Error.EMAIL_NOT_EXIST);
        } else if (passwordResetTokenRepository.findByUserEmail(passwordResetTokenDto.getUserEmail()) != null) {
            return new OperationResult(Error.PASSWORD_RESET_TOKEN_NOT_UNIQUE);
        }
        var passwordResetToken = new PasswordResetToken();
        passwordResetToken.setToken(UUID.randomUUID().toString());
        passwordResetToken.setExpiryDate(LocalDate.now().plusDays(3));
        passwordResetToken.setUser(userRepository.findByEmail(passwordResetTokenDto.getUserEmail()));
        passwordResetTokenRepository.save(passwordResetToken);
        return new OperationResult(Success.PASSWORD_RESET_TOKEN_CREATE);
    }

    @Override
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

    @Override
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
            var objectWriter = new ObjectMapper().addMixIn(PrintWriter.class, JsonIgnoreType.class).writer();
            successOperationResult.setJson(objectWriter.writeValueAsString(userResetTokenDto));
        } catch (JsonProcessingException e) {
            return new OperationResult(Error.BASE_OPERATION);
        }
        return successOperationResult;
    }

    @Override
    public List<ShortUserDto> searchForUserToNotify() {
        var userIds = diaryServiceFeignClient.findUserIds();

        return StreamSupport.stream(userRepository.findAllById(userIds).spliterator(), false)
                .map(user -> new ShortUserConverter().convert(user))
                .collect(Collectors.toList());
    }
}
