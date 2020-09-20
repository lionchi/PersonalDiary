package ru.jpixel.personaldiaryservice.repositories.open;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.jpixel.personaldiaryservice.domain.open.Diary;

public interface DiaryRepository extends CrudRepository<Diary, Long> {
    @Query("select d.id from Diary d where d.userId = :userId")
    Long findByUserId(Long userId);
}
