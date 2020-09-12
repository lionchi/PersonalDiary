package ru.jpixel.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.text.MessageFormat;
import java.util.stream.Stream;

@RequiredArgsConstructor
public enum Error {
    UNAVAILABILITY_USER_SERVICE("code.error.unavailability_user_service", "В данный момент сервис регистрации недоступен", "The registration service is currently unavailable"),

    LOGIN_EXIST("code.error.login_exist", "Логин {0} занят", "Login {0} busy"),
    EMAIL_EXIST("code.error.email_exist", "Учетная запись с такми email уже зарегистрирована", "An account with this email is already registered"),

    AUTHORIZATION("code.error.authorization", "Ошибка авторизации. Проверьте ваши учетные данные и повторите попытку", "Authorisation error. Check your credentials and try again"),
    ACCESS_IS_DENIED("code.error.access_is_denied", "Доступ запрещен", "Access is denied"),

    PASSWORD_RESET_TOKEN_NOT_EXIST("code.error.password_reset_token_not_exist", "Отсутсвует запрос на смену пароля. Отправьте запрос", "There is no request to change the password. Send a request"),
    PASSWORD_RESET_TOKEN_NOT_UNIQUE("code.error.password_reset_token_not_unique", "Запрос на смену пароля уже был выслан. Посмотрите вашу электронную почту", "A password change request has already been sent. Check your email"),
    PASSWORD_RESET_TOKEN_EXPIRED("code.error.password_reset_token_expired", "Запрос на смену пароля истек. Повторите попытку", "Password change request expired. Try again");

    @Getter
    private final String code;
    @Getter
    private final String ruText;
    @Getter
    private final String enText;

    public static Error findByCode(String code) {
        return Stream.of(values())
                .filter(resultType -> resultType.getCode().equals(code))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(MessageFormat.format("Error with code {0} not found", code)));
    }
}
