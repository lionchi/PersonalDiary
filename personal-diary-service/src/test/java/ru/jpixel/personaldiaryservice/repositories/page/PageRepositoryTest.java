package ru.jpixel.personaldiaryservice.repositories.page;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.jpixel.personaldiaryservice.domain.open.Diary;
import ru.jpixel.personaldiaryservice.domain.open.Page;
import ru.jpixel.personaldiaryservice.domain.open.Tag;
import ru.jpixel.personaldiaryservice.repositories.BaseRepositoryTest;
import ru.jpixel.personaldiaryservice.repositories.open.DiaryRepository;
import ru.jpixel.personaldiaryservice.repositories.open.PageRepository;
import ru.jpixel.personaldiaryservice.repositories.open.TagRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PageRepositoryTest extends BaseRepositoryTest {

    @Autowired
    private DiaryRepository diaryRepository;
    @Autowired
    private PageRepository pageRepository;
    @Autowired
    private TagRepository tagRepository;

    @Test
    @DisplayName("Поиск индетификатора пользователя у которого есть страницы с тегами напоминания или уведомления")
    public void findUserIdsWithTagNotificationAndTagReminderTest() {
        var firstTag = new Tag();
        firstTag.setId(2L);
        firstTag.setNameRu("Уведомление");
        firstTag.setNameEn("Notification");
        firstTag.setCode("2");
        testEntityManager.persist(firstTag);

        var secondTag = new Tag();
        secondTag.setId(3L);
        secondTag.setNameRu("Напоминание");
        secondTag.setNameEn("Reminder");
        secondTag.setCode("3");
        testEntityManager.persist(secondTag);

        var firstDiary = new Diary();
        firstDiary.setUserId(1L);
        firstDiary.setKey(new byte[]{});
        testEntityManager.persist(firstDiary);

        var secondDiary = new Diary();
        secondDiary.setUserId(1L);
        secondDiary.setKey(new byte[]{});
        testEntityManager.persist(secondDiary);

        var now = LocalDate.now();

        var firstPage = new Page();
        firstPage.setDiary(diaryRepository.findById(firstDiary.getId()).orElseThrow());
        firstPage.setContent("Моя тествоая первая запись");
        firstPage.setRecordingSummary("Первая тестовая запись");
        firstPage.setTag(tagRepository.findById(firstTag.getId()).orElseThrow());
        firstPage.setCreateDate(LocalDateTime.of(LocalDate.of(2020, 10, 1), LocalTime.MAX));
        firstPage.setNotificationDate(now);
        testEntityManager.persist(firstPage);

        var secondPage = new Page();
        secondPage.setDiary(diaryRepository.findById(secondDiary.getId()).orElseThrow());
        secondPage.setContent("Моя тествоая вторая запись");
        secondPage.setRecordingSummary("Вторая тестовая запись");
        secondPage.setTag(tagRepository.findById(secondTag.getId()).orElseThrow());
        secondPage.setCreateDate(LocalDateTime.of(LocalDate.of(2020, 10, 12), LocalTime.MAX));
        secondPage.setConfidential(true);
        secondPage.setNotificationDate(now);
        testEntityManager.persist(secondPage);

        var userIds = pageRepository.findUserIdsWithTagNotificationAndTagReminder(List.of(Tag.CodeEnum.NOTIFICATION.getCode(),
                Tag.CodeEnum.REMINDER.getCode()), now);
        assertNotNull(userIds);
        assertEquals(2, userIds.size());
    }
}
