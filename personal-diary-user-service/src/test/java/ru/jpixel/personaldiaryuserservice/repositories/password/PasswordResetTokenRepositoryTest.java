package ru.jpixel.personaldiaryuserservice.repositories.password;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.jpixel.personaldiaryuserservice.domain.PasswordResetToken;
import ru.jpixel.personaldiaryuserservice.domain.User;
import ru.jpixel.personaldiaryuserservice.repositories.BaseRepositoryTest;
import ru.jpixel.personaldiaryuserservice.repositories.PasswordResetTokenRepository;

import java.time.LocalDate;
import java.time.Month;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PasswordResetTokenRepositoryTest extends BaseRepositoryTest {

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    private User user;
    private PasswordResetToken passwordResetToken;

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
        passwordResetToken = new PasswordResetToken();
        passwordResetToken.setToken(UUID.randomUUID().toString());
        passwordResetToken.setExpiryDate(LocalDate.now().plusDays(3));
        passwordResetToken.setUser(user);

        testEntityManager.persist(user);
        testEntityManager.persist(passwordResetToken);
    }

    @Test
    @DisplayName("Поиск токена сброса пароля по токену")
    public void findByTokenTest() {
        assertNotNull(passwordResetTokenRepository.findByToken(passwordResetToken.getToken()));
    }

    @Test
    @DisplayName("Поиск токена сброса пароля по email пользователя")
    public void findByUserEmailTest() {
        assertNotNull(passwordResetTokenRepository.findByUserEmail(user.getEmail()));
    }
}
