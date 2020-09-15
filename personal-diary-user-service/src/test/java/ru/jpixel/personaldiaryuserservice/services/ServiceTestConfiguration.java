package ru.jpixel.personaldiaryuserservice.services;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.jpixel.personaldiaryuserservice.repositories.PasswordResetTokenRepository;
import ru.jpixel.personaldiaryuserservice.repositories.RoleRepository;
import ru.jpixel.personaldiaryuserservice.repositories.UserRepository;

@TestConfiguration
public class ServiceTestConfiguration {

    @Bean
    public UserService userService(UserRepository uR, RoleRepository rR, PasswordResetTokenRepository prtR, BCryptPasswordEncoder bCrypt) {
       return new UserService(uR, rR, prtR, bCrypt);
    }
}
