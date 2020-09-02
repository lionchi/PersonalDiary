package ru.jpixel.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Error {
    UNAVAILABILITY_USER_SERVICE("code.error.unavailability_user_service", "В данный момент сервис регистрации недоступен", "The registration service is currently unavailable"),

    LOGIN_EXIST("code.error.login_exist", "Логин {0} занят", "Login {0} busy"),
    EMAIL_EXIST("code.error.email_exist", "Учетная запись с такми email уже зарегистрирована", "An account with this email is already registered"),

    AUTHORIZATION("code.error.authorization", "Ошибка авторизации. Проверьте ваши учетные данные и повторите попытку", "Authorisation error. Check your credentials and try again"),
    ACCESS_IS_DENIED("code.error.access_is_denied", "Доступ запрещен", "Access is denied");

    @Getter
    private final String code;
    @Getter
    private final String ruText;
    @Getter
    private final String enText;
}
