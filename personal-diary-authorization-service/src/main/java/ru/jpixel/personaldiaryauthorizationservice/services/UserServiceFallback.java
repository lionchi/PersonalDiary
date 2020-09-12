package ru.jpixel.personaldiaryauthorizationservice.services;

import org.springframework.stereotype.Component;
import ru.jpixel.models.dtos.UserDto;

@Component
public class UserServiceFallback implements UserServiceFeignClient {
    @Override
    public UserDto findByLogin(String login) {
        return null;
    }
}
