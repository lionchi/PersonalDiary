package ru.jpixel.personaldiarymailservice.services.mail;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.mail.javamail.JavaMailSender;
import org.thymeleaf.spring5.SpringTemplateEngine;
import ru.jpixel.models.dtos.common.Error;
import ru.jpixel.models.dtos.common.OperationResult;
import ru.jpixel.models.dtos.common.Success;
import ru.jpixel.models.dtos.secr.PasswordResetTokenDto;
import ru.jpixel.models.dtos.secr.ShortUserDto;
import ru.jpixel.models.dtos.secr.UserResetTokenDto;
import ru.jpixel.personaldiarymailservice.services.BaseServiceTest;
import ru.jpixel.personaldiarymailservice.services.MailService;
import ru.jpixel.personaldiarymailservice.services.UserServiceFeignClient;

import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

public class MailServiceImplTest {

    private static class InnerClass extends BaseServiceTest {
        @MockBean
        protected JavaMailSender mailSender;
        @SpyBean
        protected SpringTemplateEngine templateEngine;
        @MockBean
        protected UserServiceFeignClient userServiceFeignClient;
        @Autowired
        protected MailService mailService;

        @Value("${message.redirect.host}")
        protected String host;
    }

    @Nested
    @DisplayName("Проверка работы метода sendRecoveryPasswordMail")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    public class SendRecoveryPasswordMail extends InnerClass {

        private String ln;
        private PasswordResetTokenDto passwordResetTokenDto;

        @BeforeAll
        public void init() {
            ln = "ru";
            passwordResetTokenDto = new PasswordResetTokenDto();
            passwordResetTokenDto.setUserEmail("test@mail.ru");
        }

        @Test
        public void sendRecoveryPasswordMailFirstCaseTest() {
            var operationResult = new OperationResult(Error.PASSWORD_RESET_TOKEN_NOT_UNIQUE);

            Mockito.when(userServiceFeignClient.createPasswordResetToken(any(PasswordResetTokenDto.class)))
                    .thenReturn(operationResult);

            var actualOperationResult = assertDoesNotThrow(() -> mailService.sendRecoveryPasswordMail(passwordResetTokenDto, ln));

            assertEquals(operationResult.getCode(), actualOperationResult.getCode());
        }

        @Test
        public void sendRecoveryPasswordMailSecondCaseTest() {
            var sendRecoveryPasswordMailOperationResult = new OperationResult(Success.PASSWORD_RESET_TOKEN_CREATE);

            Mockito.when(userServiceFeignClient.createPasswordResetToken(any(PasswordResetTokenDto.class)))
                    .thenReturn(sendRecoveryPasswordMailOperationResult);

            var findTokenByEmailOperationResult = new OperationResult(Error.PASSWORD_RESET_TOKEN_NOT_EXIST);

            Mockito.when(userServiceFeignClient.findTokenByEmail(anyString()))
                    .thenReturn(findTokenByEmailOperationResult);

            var actualOperationResult = assertDoesNotThrow(() -> mailService.sendRecoveryPasswordMail(passwordResetTokenDto, ln));

            assertEquals(findTokenByEmailOperationResult.getCode(), actualOperationResult.getCode());
        }

        @Test
        public void sendRecoveryPasswordMailThirdCaseTest() throws JsonProcessingException {
            var sendRecoveryPasswordMailOperationResult = new OperationResult(Success.PASSWORD_RESET_TOKEN_CREATE);

            Mockito.when(userServiceFeignClient.createPasswordResetToken(any(PasswordResetTokenDto.class)))
                    .thenReturn(sendRecoveryPasswordMailOperationResult);

            var findTokenByEmailOperationResult = new OperationResult(Success.BASE_OPERATION);

            var userResetTokenDto = new UserResetTokenDto();
            userResetTokenDto.setName("admin");
            userResetTokenDto.setToken(UUID.randomUUID().toString());

            findTokenByEmailOperationResult.setJson(new ObjectMapper().writeValueAsString(userResetTokenDto));

            Mockito.when(userServiceFeignClient.findTokenByEmail(anyString()))
                    .thenReturn(findTokenByEmailOperationResult);

            Mockito.when(mailSender.createMimeMessage())
                    .thenReturn(Mockito.mock(MimeMessage.class));

            var actualOperationResult = assertDoesNotThrow(() -> mailService.sendRecoveryPasswordMail(passwordResetTokenDto, ln));

            assertEquals(Success.RECOVERY_PASSWORD_SEND_MESSAGE.getCode(), actualOperationResult.getCode());
        }
    }

    @Nested
    @DisplayName("Проверка работы метода sendRecoveryPasswordMail")
    public class SendNotificationMail extends InnerClass {

        @Test
        public void sendNotificationMailTest() {
            var shortUserDto = new ShortUserDto();
            shortUserDto.setName("Тест Тестов");
            shortUserDto.setEmail("test@mail.ru");

            Mockito.when(userServiceFeignClient.searchForUserToNotify())
                    .thenReturn(List.of(shortUserDto));

            Mockito.when(mailSender.createMimeMessage())
                    .thenReturn(Mockito.mock(MimeMessage.class));

            assertDoesNotThrow(() -> mailService.sendNotificationMail());
        }
    }
}
