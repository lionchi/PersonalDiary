package ru.jpixel.personaldiarymailservice.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import ru.jpixel.models.dtos.common.Error;
import ru.jpixel.models.dtos.common.*;
import ru.jpixel.models.dtos.secr.PasswordResetTokenDto;
import ru.jpixel.models.dtos.secr.UserResetTokenDto;

import javax.mail.MessagingException;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.HashMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

    private final JavaMailSender emailSender;
    private final SpringTemplateEngine templateEngine;
    private final UserServiceFeignClient userServiceFeignClient;

    @Value("${message.redirect.host}")
    private String host;

    @Override
    public OperationResult sendRecoveryPasswordMail(PasswordResetTokenDto passwordResetTokenDto, String ln) {
        var operationResultCreateToken = userServiceFeignClient.createPasswordResetToken(passwordResetTokenDto);
        if (ResultType.findByType(operationResultCreateToken.getResultType()) == ResultType.ERROR) {
            return operationResultCreateToken;
        }
        var operationResultFindTokenByEmail = userServiceFeignClient.findTokenByEmail(passwordResetTokenDto.getUserEmail());
        if (ResultType.findByType(operationResultFindTokenByEmail.getResultType()) == ResultType.ERROR) {
            return operationResultFindTokenByEmail;
        }
        try {
            var userResetTokenDto = new ObjectMapper().readValue(operationResultFindTokenByEmail.getJson(), UserResetTokenDto.class);
            var model = new HashMap<String, Object>();
            model.put("fio", userResetTokenDto.getName());
            model.put("resetUrl", host + "/recovery-password?token=" + userResetTokenDto.getToken());
            var message = emailSender.createMimeMessage();
            var helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
            var context = new Context();
            context.setVariables(model);
            var templatePath = MessageFormat.format("email/recovery-password-email-template-{0}.html", ln);
            var html = templateEngine.process(templatePath, context);
            helper.setTo(passwordResetTokenDto.getUserEmail());
            helper.setText(html, true);
            helper.setSubject(Language.findByCode(ln) == Language.RUSSIAN ? "Запрос на сброс пароля" : "Password reset request");
            emailSender.send(message);
        } catch (MessagingException | JsonProcessingException e) {
            return new OperationResult(Error.RECOVERY_PASSWORD_NOT_SEND_MESSAGE);
        }
        return new OperationResult(Success.RECOVERY_PASSWORD_SEND_MESSAGE);
    }

    @Override
    @Scheduled(cron = "0 0 10,14,18 ? * *")
    public void sendNotificationMail() {
        var userList = userServiceFeignClient.searchForUserToNotify();
        for (var user : userList) {
            try {
                var model = new HashMap<String, Object>();
                model.put("name", user.getName());
                model.put("redirectUrl", host);
                var message = emailSender.createMimeMessage();
                var helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
                var context = new Context();
                context.setVariables(model);
                var html = templateEngine.process("email/notification-email-template-ru.html", context);
                helper.setTo(user.getEmail());
                helper.setText(html, true);
                helper.setSubject("Ваши события дня");
                emailSender.send(message);
            } catch (MessagingException e) {
                log.error("Ошибка при отправке уведомлений пользователям", e);
            }
        }
    }
}
