package ru.jpixel.personaldiaryuserservice.repositories;


import org.springframework.data.repository.CrudRepository;
import ru.jpixel.personaldiaryuserservice.domain.User;

public interface UserRepository extends CrudRepository<User, Long> {

    User findByLogin(String login);

    boolean existsByLogin(String login);
    boolean existsByEmail(String email);
}
