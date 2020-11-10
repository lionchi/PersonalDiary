package ru.jpixel.personaldiaryuserservice.services;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "personal-diary-service", fallback = DiaryServiceFallback.class)
public interface DiaryServiceFeignClient {

    @GetMapping("findUserIds")
    List<Long> findUserIds();
}
