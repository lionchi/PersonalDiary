package ru.jpixel.personaldiaryuserservice.repositories.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.jpixel.personaldiaryuserservice.domain.secr.User;
import ru.jpixel.personaldiaryuserservice.repositories.BaseRepositoryTest;
import ru.jpixel.personaldiaryuserservice.repositories.secr.UserRepository;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;

public class UserRepositoryTest extends BaseRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    public void init() {
        user = new User();
        user.setName("Иван");
        user.setPassword("11111");
        user.setLogin("test");
        user.setEmail("test@mail.ru");
        user.setPrefix("+7");
        user.setPhone("9109109191");
        user.setBirthday(LocalDate.of(1995, Month.JANUARY, 1));

        testEntityManager.persist(user);
    }

    @Test
    @DisplayName("Поиск пользователя по логину")
    public void findByLoginTest() {
        assertNotNull(userRepository.findByLogin(user.getLogin()));
    }

    @Test
    @DisplayName("Поиск пользователя по email")
    public void findByEmailTest() {
        assertNotNull(userRepository.findByEmail(user.getEmail()));
    }

    @Test
    @DisplayName("Обновление пароля пользователя")
    public void updatePasswordTest() {
        userRepository.updatePassword("22222", user.getId());

        testEntityManager.clear();

        var foundUser = userRepository.findById(user.getId()).orElse(null);

        assertNotNull(foundUser);
        assertEquals("22222", foundUser.getPassword());
    }

    @Test
    @DisplayName("Существует ли пользователь с такми логином")
    public void existsByLoginTest() {
        assertFalse(userRepository.existsByLogin("test2"));
        assertTrue(userRepository.existsByLogin(user.getLogin()));
    }

    @Test
    @DisplayName("Существует ли пользователь с такми email")
    public void existsByEmailTest() {
        assertFalse(userRepository.existsByEmail("test2@mail.ru"));
        assertTrue(userRepository.existsByEmail(user.getEmail()));
    }
}
