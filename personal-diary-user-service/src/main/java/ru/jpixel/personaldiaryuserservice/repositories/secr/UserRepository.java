package ru.jpixel.personaldiaryuserservice.repositories.secr;


import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.jpixel.personaldiaryuserservice.domain.secr.User;

public interface UserRepository extends CrudRepository<User, Long> {

    User findByLogin(String login);
    User findByEmail(String email);

    @Modifying
    @Query("update User u set u.password = :password where u.id = :id")
    void updatePassword(String password, Long id);

    boolean existsByLogin(String login);
    boolean existsByEmail(String email);
}
