package ru.jpixel.personaldiaryauthorizationservice.services;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class ServiceTestConfiguration {

    @Bean
    public PersonalDiaryUserDetailsService userDetailsService(UserServiceFeignClient usfc, DiaryServiceFeignClient dsfc) {
        return new PersonalDiaryUserDetailsService(usfc, dsfc);
    }
}
