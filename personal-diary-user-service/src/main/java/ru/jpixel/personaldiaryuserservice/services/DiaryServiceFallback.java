package ru.jpixel.personaldiaryuserservice.services;

import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class DiaryServiceFallback implements DiaryServiceFeignClient {
    @Override
    public List<Long> findUserIds() {
        return Collections.emptyList();
    }
}
