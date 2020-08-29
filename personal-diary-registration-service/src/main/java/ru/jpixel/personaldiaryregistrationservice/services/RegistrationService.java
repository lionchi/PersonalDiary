package ru.jpixel.personaldiaryregistrationservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.jpixel.models.OperationResult;
import ru.jpixel.models.Error;
import ru.jpixel.models.Success;
import ru.jpixel.models.dtos.UserDto;
import ru.jpixel.personaldiaryregistrationservice.domain.User;
import ru.jpixel.personaldiaryregistrationservice.repositories.RoleRepository;
import ru.jpixel.personaldiaryregistrationservice.repositories.UserRepository;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public OperationResult registrationUser(UserDto userDto) {
        if (userRepository.existsByLogin(userDto.getLogin())) {
            return new OperationResult(Error.LOGIN_EXIST);
        } else if (userRepository.existsByEmail(userDto.getEmail())) {
            return new OperationResult(Error.EMAIL_EXIST);
        }
        var user = new User();
        user.setName(userDto.getName());
        user.setLogin(userDto.getLogin());
        user.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        user.setEmail(userDto.getEmail());
        user.setPrefix(userDto.getPrefix());
        user.setPhone(userDto.getPhone());
        user.setBirthday(userDto.getBirthday());
        user.setRoles(Collections.singletonList(roleRepository.findByName("USER")));
        userRepository.save(user);
        return new OperationResult(Success.REGISTRATION);
    }
}
