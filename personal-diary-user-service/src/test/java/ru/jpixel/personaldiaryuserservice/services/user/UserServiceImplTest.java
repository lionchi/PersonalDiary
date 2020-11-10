package ru.jpixel.personaldiaryuserservice.services.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.binary.Base64;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.jpixel.models.dtos.common.Error;
import ru.jpixel.models.dtos.common.OperationResult;
import ru.jpixel.models.dtos.common.Success;
import ru.jpixel.models.dtos.secr.PasswordResetTokenDto;
import ru.jpixel.models.dtos.secr.RecoveryPasswordDto;
import ru.jpixel.models.dtos.secr.UserDto;
import ru.jpixel.models.dtos.secr.UserResetTokenDto;
import ru.jpixel.personaldiaryuserservice.domain.secr.PasswordResetToken;
import ru.jpixel.personaldiaryuserservice.domain.secr.Role;
import ru.jpixel.personaldiaryuserservice.domain.secr.User;
import ru.jpixel.personaldiaryuserservice.repositories.secr.PasswordResetTokenRepository;
import ru.jpixel.personaldiaryuserservice.repositories.secr.RoleRepository;
import ru.jpixel.personaldiaryuserservice.repositories.secr.UserRepository;
import ru.jpixel.personaldiaryuserservice.services.BaseServiceTest;
import ru.jpixel.personaldiaryuserservice.services.DiaryServiceFeignClient;
import ru.jpixel.personaldiaryuserservice.services.UserService;

import java.time.LocalDate;
import java.time.Month;
import java.util.Collections;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;

public class UserServiceImplTest {

    private static class InnerClass extends BaseServiceTest {
        @MockBean
        protected UserRepository userRepository;
        @MockBean
        protected RoleRepository roleRepository;
        @MockBean
        protected PasswordResetTokenRepository passwordResetTokenRepository;
        @MockBean
        protected BCryptPasswordEncoder bCryptPasswordEncoder;
        @MockBean
        protected DiaryServiceFeignClient diaryServiceFeignClient;
        @Autowired
        protected UserService userService;
    }

    @Nested
    @DisplayName("Проверка работы метода save")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    public class Save extends InnerClass {

        private UserDto userDto;

        @BeforeAll
        public void init() {
            userDto = new UserDto();
            userDto.setName("Иван");
            userDto.setPassword("11111");
            userDto.setLogin("test");
            userDto.setEmail("test@mail.ru");
            userDto.setPrefix("+7");
            userDto.setPhone("9109109191");

            Mockito.when(bCryptPasswordEncoder.encode(anyString()))
                    .thenAnswer(invocationOnMock -> new String(Base64.encodeBase64(((String) invocationOnMock.getArgument(0)).getBytes())));
        }

        @Test
        @DisplayName("LOGIN_EXIST")
        public void returnOperationResultLoginExist() {
            Mockito.when(userRepository.existsByLogin(anyString()))
                    .thenReturn(true);
            var resultSave = userService.save(userDto);

            assertEquals(Error.LOGIN_EXIST.getCode(), resultSave.getCode());
        }

        @Test
        @DisplayName("EMAIL_EXIST")
        public void returnOperationResultEmailExist() {
            Mockito.when(userRepository.existsByEmail(anyString()))
                    .thenReturn(true);
            var resultSave = userService.save(userDto);

            assertEquals(Error.EMAIL_EXIST.getCode(), resultSave.getCode());
        }

        @Test
        @DisplayName("Сохранение пользователя")
        public void saveTest() {
            Mockito.when(userRepository.existsByLogin(anyString()))
                    .thenReturn(false);
            Mockito.when(userRepository.existsByEmail(anyString()))
                    .thenReturn(false);
            OperationResult resultSave = userService.save(userDto);

            assertEquals(Success.REGISTRATION.getCode(), resultSave.getCode());
        }
    }

    @Nested
    @DisplayName("Проверка работы метода findByLogin")
    public class FindByLogin extends InnerClass {
        @Test
        @DisplayName("Поиск пользователя по логину")
        public void findByLoginTest() {
            var user = new User();
            user.setId(999L);
            user.setName("Иван");
            user.setPassword("11111");
            user.setLogin("test");
            user.setEmail("test@mail.ru");
            user.setPrefix("+7");
            user.setPhone("9109109191");
            user.setBirthday(LocalDate.of(1995, Month.JANUARY, 1));
            var role = new Role();
            role.setId(999L);
            role.setName("TEST");
            user.setRoles(Collections.singletonList(role));

            Mockito.when(userRepository.findByLogin(anyString()))
                    .thenReturn(user);

            var foundUser = userService.findByLogin(anyString());
            assertEquals(user.getId(), foundUser.getId());
        }
    }

    @Nested
    @DisplayName("Проверка работы метода createPasswordResetToken")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    public class CreatePasswordResetToken extends InnerClass {

        private User user;
        private PasswordResetTokenDto passwordResetTokenDto;

        @BeforeAll
        public void init() {
            user = new User();
            user.setId(999L);
            user.setName("Иван");
            user.setPassword("11111");
            user.setLogin("test");
            user.setEmail("test@mail.ru");
            user.setPrefix("+7");
            user.setPhone("9109109191");
            user.setBirthday(LocalDate.of(1995, Month.JANUARY, 1));

            passwordResetTokenDto = new PasswordResetTokenDto();
            passwordResetTokenDto.setUserEmail(user.getEmail());
        }

        @Test
        @DisplayName("EMAIL_NOT_EXIST")
        public void returnOperationResultEmailNotExist() {
            Mockito.when(userRepository.existsByEmail(anyString()))
                    .thenReturn(false);
            var resultCreate = userService.createPasswordResetToken(passwordResetTokenDto);

            assertEquals(Error.EMAIL_NOT_EXIST.getCode(), resultCreate.getCode());
        }

        @Test
        @DisplayName("PASSWORD_RESET_TOKEN_NOT_UNIQUE")
        public void returnOperationResultPasswordResetTokenNotUnique() {
            Mockito.when(userRepository.existsByEmail(anyString()))
                    .thenReturn(true);
            Mockito.when(passwordResetTokenRepository.findByUserEmail(anyString()))
                    .thenReturn(new PasswordResetToken());
            var resultCreate = userService.createPasswordResetToken(passwordResetTokenDto);

            assertEquals(Error.PASSWORD_RESET_TOKEN_NOT_UNIQUE.getCode(), resultCreate.getCode());
        }

        @Test
        @DisplayName("Создание токена")
        public void createPasswordResetTokenTest() {
            Mockito.when(userRepository.existsByEmail(anyString()))
                    .thenReturn(true);
            Mockito.when(passwordResetTokenRepository.findByUserEmail(anyString()))
                    .thenReturn(null);
            Mockito.when(userRepository.findByEmail(anyString()))
                    .thenReturn(user);
            var resultCreate = userService.createPasswordResetToken(passwordResetTokenDto);

            assertEquals(Success.PASSWORD_RESET_TOKEN_CREATE.getCode(), resultCreate.getCode());
        }
    }

    @Nested
    @DisplayName("Проверка работы метода recoveryPassword")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    public class RecoveryPassword extends InnerClass {

        private PasswordResetToken passwordResetToken;
        private RecoveryPasswordDto recoveryPasswordDto;

        @BeforeAll
        public void init() {
            passwordResetToken = new PasswordResetToken();
            passwordResetToken.setId(999L);
            passwordResetToken.setToken(UUID.randomUUID().toString());
            passwordResetToken.setUser(new User());

            recoveryPasswordDto = new RecoveryPasswordDto();
            recoveryPasswordDto.setNewPassword("11111");
            recoveryPasswordDto.setToken(UUID.randomUUID().toString());
        }

        @Test
        @DisplayName("PASSWORD_RESET_TOKEN_NOT_EXIST")
        public void returnOperationResultPasswordResetTokenNotExist() {
            Mockito.when(passwordResetTokenRepository.findByToken(anyString()))
                    .thenReturn(null);
            var resultRecoveryPassword = userService.recoveryPassword(recoveryPasswordDto);

            assertEquals(Error.PASSWORD_RESET_TOKEN_NOT_EXIST.getCode(), resultRecoveryPassword.getCode());
        }

        @Test
        @DisplayName("PASSWORD_RESET_TOKEN_EXPIRED")
        public void returnOperationResultResetTokenExpired() {
            passwordResetToken.setExpiryDate(LocalDate.now().minusDays(4));
            Mockito.when(passwordResetTokenRepository.findByToken(anyString()))
                    .thenReturn(passwordResetToken);
            Mockito.doNothing().when(passwordResetTokenRepository).delete(any());
            var resultRecoveryPassword = userService.recoveryPassword(recoveryPasswordDto);

            assertEquals(Error.PASSWORD_RESET_TOKEN_EXPIRED.getCode(), resultRecoveryPassword.getCode());
        }

        @Test
        @DisplayName("Изменение пароля")
        public void recoveryPasswordTest() {
            passwordResetToken.setExpiryDate(LocalDate.now().plusDays(3));
            Mockito.when(passwordResetTokenRepository.findByToken(anyString()))
                    .thenReturn(passwordResetToken);
            Mockito.doNothing().when(userRepository).updatePassword(anyString(), anyLong());
            Mockito.doNothing().when(passwordResetTokenRepository).delete(passwordResetToken);
            var resultRecoveryPassword = userService.recoveryPassword(recoveryPasswordDto);

            assertEquals(Success.RECOVERY_PASSWORD.getCode(), resultRecoveryPassword.getCode());
        }
    }

    @Nested
    @DisplayName("Проверка работы метода findTokenByEmail")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    public class FindTokenByEmail extends InnerClass {
        private PasswordResetToken passwordResetToken;

        @BeforeAll
        public void init() {
            passwordResetToken = new PasswordResetToken();
            passwordResetToken.setId(999L);
            passwordResetToken.setExpiryDate(LocalDate.now().plusDays(3));
            passwordResetToken.setToken(UUID.randomUUID().toString());
            passwordResetToken.setUser(new User());
        }

        @Test
        @DisplayName("PASSWORD_RESET_TOKEN_NOT_EXIST")
        public void returnOperationResultPasswordResetTokenNotExist() {
            Mockito.when(passwordResetTokenRepository.findByUserEmail(anyString()))
                    .thenReturn(null);
            var resultFind = userService.findTokenByEmail(anyString());

            assertEquals(Error.PASSWORD_RESET_TOKEN_NOT_EXIST.getCode(), resultFind.getCode());
        }

        @Test
        @DisplayName("Поиск токена по email")
        public void findTokenByEmailTest() throws JsonProcessingException {
            Mockito.when(passwordResetTokenRepository.findByUserEmail(anyString()))
                    .thenReturn(passwordResetToken);
            var resultFind = userService.findTokenByEmail(anyString());

            assertEquals(Success.BASE_OPERATION.getCode(), resultFind.getCode());

            var userResetTokenDto = new ObjectMapper().readValue(resultFind.getJson(), UserResetTokenDto.class);

            assertEquals(passwordResetToken.getToken(), userResetTokenDto.getToken());
        }
    }

    @Nested
    @DisplayName("Проверка работы метода searchForUserToNotify")
    public class SearchForUserToNotifyTest extends InnerClass {
        @Test
        @DisplayName("Поиск всех пользователей, у которых есть уведомления или напоминания")
        public void searchForUserToNotifyTest() {
            Mockito.when(diaryServiceFeignClient.findUserIds())
                    .thenReturn(Collections.emptyList());

            Mockito.when(userRepository.findAllById(anyIterable()))
                    .thenReturn(Collections.emptyList());

            assertDoesNotThrow(() -> userService.searchForUserToNotify());
        }
    }
}
