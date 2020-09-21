package ru.jpixel.personaldiaryservice.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import ru.jpixel.personaldiaryservice.domain.Directory;

@NoRepositoryBean
public interface DirectoryRepository<T extends Directory> extends CrudRepository<T, Long> {
    @Query("select e from #{#entityName} e where e.code = :code")
    T findByCode(String code);
}
