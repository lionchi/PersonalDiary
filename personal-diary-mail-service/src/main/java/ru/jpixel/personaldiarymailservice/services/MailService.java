package ru.jpixel.personaldiarymailservice.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import ru.jpixel.models.Error;
import ru.jpixel.models.OperationResult;
import ru.jpixel.models.ResultType;
import ru.jpixel.models.Success;
import ru.jpixel.models.dtos.PasswordResetTokenRequest;
import ru.jpixel.models.dtos.UserResetTokenDto;

import javax.mail.MessagingException;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender emailSender;
    private final SpringTemplateEngine templateEngine;
    private final UserServiceFeignClient userServiceFeignClient;

    @Value("${message.redirect.host}")
    private String host;

    public OperationResult sendRecoveryPasswordMail(PasswordResetTokenRequest passwordResetTokenRequest, String ln) {
        var operationResultCreateToken = userServiceFeignClient.createPasswordResetToken(passwordResetTokenRequest);
        if (operationResultCreateToken.getResultTypeEnum() == ResultType.ERROR) {
            return operationResultCreateToken;
        }
        var operationResultFindTokenByEmail = userServiceFeignClient.findTokenByEmail(passwordResetTokenRequest.getUserEmail());
        if (operationResultFindTokenByEmail.getResultTypeEnum() == ResultType.ERROR) {
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
            helper.setTo(passwordResetTokenRequest.getUserEmail());
            helper.setText(html, true);
            helper.setSubject("Запрос на сброс пароля");
            emailSender.send(message);
        } catch (MessagingException | JsonProcessingException e) {
            return new OperationResult(Error.RECOVERY_PASSWORD_NOT_SEND_MESSAGE);
        }
        return new OperationResult(Success.RECOVERY_PASSWORD_SEND_MESSAGE);
    }
}
