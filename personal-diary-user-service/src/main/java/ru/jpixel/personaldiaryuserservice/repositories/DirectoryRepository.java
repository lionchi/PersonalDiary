package ru.jpixel.personaldiaryuserservice.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import ru.jpixel.personaldiaryuserservice.domain.Directory;

@NoRepositoryBean
public interface DirectoryRepository<T extends Directory> extends CrudRepository<T, Long> {
}
