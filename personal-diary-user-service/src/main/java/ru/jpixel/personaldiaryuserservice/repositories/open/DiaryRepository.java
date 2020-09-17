package ru.jpixel.personaldiaryuserservice.repositories.open;

import org.springframework.data.repository.CrudRepository;
import ru.jpixel.personaldiaryuserservice.domain.open.Diary;

public interface DiaryRepository extends CrudRepository<Diary, Long> {
}
