package ru.jpixel.personaldiaryuserservice.services;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.jpixel.models.Error;
import ru.jpixel.models.OperationResult;
import ru.jpixel.models.Success;
import ru.jpixel.models.dtos.UserDto;
import ru.jpixel.personaldiaryuserservice.domain.Role;
import ru.jpixel.personaldiaryuserservice.domain.User;
import ru.jpixel.personaldiaryuserservice.repositories.RoleRepository;
import ru.jpixel.personaldiaryuserservice.repositories.UserRepository;

import java.util.Collections;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
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
        User foundUser = userRepository.findByLogin(login);
        if (foundUser == null) {
            return  null;
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
}
