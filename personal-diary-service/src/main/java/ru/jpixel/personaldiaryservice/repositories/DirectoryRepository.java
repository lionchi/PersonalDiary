package ru.jpixel.personaldiaryservice.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import ru.jpixel.personaldiaryservice.domain.Directory;

@NoRepositoryBean
public interface DirectoryRepository<T extends Directory> extends CrudRepository<T, Long> {
}
