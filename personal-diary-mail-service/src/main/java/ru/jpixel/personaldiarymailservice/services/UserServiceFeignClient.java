package ru.jpixel.personaldiarymailservice.services;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.jpixel.models.dtos.common.OperationResult;
import ru.jpixel.models.dtos.secr.PasswordResetTokenDto;
import ru.jpixel.models.dtos.secr.ShortUserDto;

import java.util.List;

@FeignClient(name = "personal-diary-user-service", fallback = UserServiceFallback.class)
public interface UserServiceFeignClient {

    @PostMapping("create/passwordResetToken")
    OperationResult createPasswordResetToken(@RequestBody PasswordResetTokenDto passwordResetTokenDto);

    @GetMapping("findTokenByEmail/{email}")
    OperationResult findTokenByEmail(@PathVariable String email);

    @GetMapping("searchForUserToNotify")
    List<ShortUserDto> searchForUserToNotify();
}
