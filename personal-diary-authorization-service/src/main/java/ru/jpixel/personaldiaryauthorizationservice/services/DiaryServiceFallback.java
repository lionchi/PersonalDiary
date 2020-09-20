package ru.jpixel.personaldiaryauthorizationservice.services;

import org.springframework.stereotype.Component;

@Component
public class DiaryServiceFallback implements DiaryServiceFeignClient {
    @Override
    public Long findDiaryIdByUserId(Long userId) {
        return null;
    }
}
