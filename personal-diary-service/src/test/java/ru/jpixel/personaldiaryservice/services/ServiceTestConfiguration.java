package ru.jpixel.personaldiaryservice.services;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import ru.jpixel.personaldiaryservice.repositories.open.DiaryRepository;
import ru.jpixel.personaldiaryservice.repositories.open.PageRepository;
import ru.jpixel.personaldiaryservice.repositories.open.TagRepository;

@TestConfiguration
public class ServiceTestConfiguration {

    @Bean
    public DiaryService diaryService(DiaryRepository diaryRepository, TagRepository tagRepository,
                                         PageRepository pageRepository, PageApplySearchParams pageApplySearchParams) {
        return new DiaryServiceImpl(diaryRepository, tagRepository, pageRepository, pageApplySearchParams);
    }
}
