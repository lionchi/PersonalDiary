package ru.jpixel.personaldiaryservice.repositories.open;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import ru.jpixel.personaldiaryservice.domain.open.Page;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

public interface PageRepository extends JpaRepository<Page, Long>, JpaSpecificationExecutor<Page> {
    @Query("select distinct page.diary.userId from Page page where page.tag.code in :codes and page.notificationDate = :now")
    List<Long> findUserIdsWithTagNotificationAndTagReminder(Collection<String> codes, LocalDate now);
}
