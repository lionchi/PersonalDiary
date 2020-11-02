package ru.jpixel.personaldiaryservice.repositories.diary;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.jpixel.personaldiaryservice.domain.open.Diary;
import ru.jpixel.personaldiaryservice.repositories.BaseRepositoryTest;
import ru.jpixel.personaldiaryservice.repositories.open.DiaryRepository;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DiaryRepositoryTest extends BaseRepositoryTest {

    @Autowired
    private DiaryRepository diaryRepository;

    @Test
    @DisplayName("Поиск идентификатора дневника по идентификатору пользователя")
    public void findByUserIdTest() {
        var diary = new Diary();
        diary.setUserId(1L);
        diary.setKey(new byte[]{});
        testEntityManager.persist(diary);
        assertNotNull(diaryRepository.findByUserId(diary.getUserId()));
    }
}
