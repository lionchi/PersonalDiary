package ru.jpixel.personaldiaryauthorizationservice.services;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.jpixel.models.OperationResult;
import ru.jpixel.models.dtos.UserDto;

@FeignClient(name = "personal-diary-user-service", fallback = UserServiceFallback.class)
public interface UserServiceFeignClient {

    @GetMapping("findByLogin/{login}")
    UserDto findByLogin(@PathVariable String login);
}