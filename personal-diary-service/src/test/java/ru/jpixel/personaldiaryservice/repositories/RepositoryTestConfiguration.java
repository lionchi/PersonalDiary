package ru.jpixel.personaldiaryservice.repositories;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import ru.jpixel.personaldiaryservice.services.PageApplySearchParams;

@TestConfiguration
public class RepositoryTestConfiguration {

    @Bean
    public PageApplySearchParams pageApplySearchParams() {
        return new PageApplySearchParams();
    }
}
