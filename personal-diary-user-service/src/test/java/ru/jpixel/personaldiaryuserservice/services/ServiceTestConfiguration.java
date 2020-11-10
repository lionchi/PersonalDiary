package ru.jpixel.personaldiaryuserservice.services;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.jpixel.personaldiaryuserservice.repositories.secr.PasswordResetTokenRepository;
import ru.jpixel.personaldiaryuserservice.repositories.secr.RoleRepository;
import ru.jpixel.personaldiaryuserservice.repositories.secr.UserRepository;

@TestConfiguration
public class ServiceTestConfiguration {

    @Bean
    public UserService userService(UserRepository uR, RoleRepository rR, PasswordResetTokenRepository prtR,
                                   BCryptPasswordEncoder bCrypt, DiaryServiceFeignClient dsfC) {
       return new UserServiceImpl(uR, rR, prtR, bCrypt, dsfC);
    }
}
