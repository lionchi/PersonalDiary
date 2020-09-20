package ru.jpixel.personaldiaryauthorizationservice.services;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "personal-diary-service", fallback = DiaryServiceFallback.class)
public interface DiaryServiceFeignClient {

    @GetMapping("findDiaryId")
    Long findDiaryIdByUserId(@RequestParam Long userId);
}
