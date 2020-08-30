package ru.jpixel.personaldiaryregistrationservice.services;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.jpixel.models.OperationResult;
import ru.jpixel.models.dtos.UserDto;

@FeignClient(name = "personal-diary-user-service", fallback = UserServiceFallback.class)
public interface UserServiceFeignClient {

    @PostMapping("save")
    OperationResult save(@RequestBody  UserDto userDto);
}
