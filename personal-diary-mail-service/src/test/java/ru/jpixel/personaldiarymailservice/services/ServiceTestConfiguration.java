package ru.jpixel.personaldiarymailservice.services;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.thymeleaf.spring5.SpringTemplateEngine;

@TestConfiguration
public class ServiceTestConfiguration {

    @Bean
    public MailService mailService(JavaMailSender mailSender, SpringTemplateEngine templateEngine, UserServiceFeignClient userServiceFeignClient) {
        return new MailServiceImpl(mailSender, templateEngine, userServiceFeignClient);
    }
}
