package ru.jpixel.personaldiaryregistrationservice.repositories;


import org.springframework.data.repository.CrudRepository;
import ru.jpixel.personaldiaryregistrationservice.domain.User;

public interface UserRepository extends CrudRepository<User, Long> {

    boolean existsByLogin(String login);
    boolean existsByEmail(String email);
}
