package ru.jpixel.personaldiaryservice.repositories.open;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.jpixel.personaldiaryservice.domain.open.Page;

public interface PageRepository extends JpaRepository<Page, Long>, JpaSpecificationExecutor<Page> {
    Integer countByDiaryId(Long diaryId);
}
