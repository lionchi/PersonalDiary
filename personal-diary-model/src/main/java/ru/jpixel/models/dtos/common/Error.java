package ru.jpixel.models.dtos.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.text.MessageFormat;
import java.util.stream.Stream;

@RequiredArgsConstructor
public enum Error {
    BASE_OPERATION("code.error.base_operation", "Во время операции произошла ошибка", "Operation completed successfully"),

    UNAVAILABILITY_REGISTRATION_SERVICE("code.error.unavailability_user_service",
            "В данный момент сервис регистрации недоступен", "The registration service is currently unavailable"),
    UNAVAILABILITY_USER_SERVICE("code.error.unavailability_user_service",
            "В данный момент сервис по работе с пользователями недоступен", "The password recovery token creation service is currently unavailable"),
    UNAVAILABILITY_DIARY_RESET_SERVICE("code.error.unavailability_diary_service",
            "В данный момент сервис личный дневник недоступен", "The personal diary service is currently unavailable"),

    LOGIN_EXIST("code.error.login_exist", "Логин {0} занят", "Login {0} busy"),
    LOGIN_NOT_EXIST("code.error.login_not_exist", "Учетной записи с такми логином не существует",
            "Account with this login does not exist"),
    EMAIL_EXIST("code.error.email_exist", "Учетная запись с такми email уже зарегистрирована",
            "An account with this email is already registered"),
    EMAIL_NOT_EXIST("code.error.email_not_exist", "Учетной записи с такми email не существует",
            "Account with this email does not exist"),
    NOT_DELETE_USER("code.error.not_delete_user", "Невозможно удалить пользователя. Пользователь отсутствует в системе",
            "Unable to delete user. User is not logged in"),

    AUTHORIZATION("code.error.authorization", "Ошибка авторизации. Проверьте ваши учетные данные и повторите попытку",
            "Authorisation error. Check your credentials and try again"),
    ACCESS_IS_DENIED("code.error.access_is_denied", "Доступ запрещен", "Access is denied"),

    PASSWORD_RESET_TOKEN_NOT_EXIST("code.error.password_reset_token_not_exist",
            "Отсутсвует запрос на смену пароля. Отправьте запрос", "There is no request to change the password. Send a request"),
    PASSWORD_RESET_TOKEN_NOT_UNIQUE("code.error.password_reset_token_not_unique",
            "Запрос на смену пароля уже был выслан. Посмотрите вашу электронную почту", "A password change request has already been sent. Check your email"),
    PASSWORD_RESET_TOKEN_EXPIRED("code.error.password_reset_token_expired",
            "Запрос на смену пароля истек. Повторите попытку", "Password change request expired. Try again"),

    RECOVERY_PASSWORD_NOT_SEND_MESSAGE("code.success.recovery_password_send_message", "Ошибка во время отправки сообщения. Повторите попытку",
            "An error occurred while sending the message. Try again"),

    NOT_CREATE_DIARY("code.error.not_create_diary", "Произошла ошбика при создании личного дневника. Повторите попытку или обратитесь к администратору",
            "An error occurred while creating a personal diary. Please try again or contact your administrator"),
    NOT_DELETE_DIARY("code.error.not_delete_diary", "Невозможно удалить дневник. Дневник отсутствует в системе",
            "Unable to delete the diary. Diary is missing from the system"),
    NOT_CREATE_PAGE_DIARY_ID("code.error.not_create_page", "Отсутсвует дневник для добавления новых записей. Создайте дневник",
                             "There is no diary for adding new entries. Create a diary"),
    NOT_CREATE_PAGE("code.error.not_create_page", "Произошла ошбика при попытку создать запись в дневнике. Повторите попытку или обратитесь к администратору",
            "An error occurred while trying to create a diary entry. Please try again or contact your administrator"),
    NOT_EDIT_PAGE("code.error.not_edit_page", "Произошла ошбика при попытке отредактировать запись в дневнике. Повторите попытку или обратитесь к администратору",
                            "An error occurred while trying to edit a diary entry. Please try again or contact your administrator"),
    NOT_EDIT_PAGE_ID("code.error.not_edit_page_id", "Неверный идентификатор записи для обновления данных", "Invalid record ID for data update"),
    NOT_DELETE_PAGE("code.error.not_delete_page", "Невозможно удалить запись. Запись отсутствует в системе", "Unable to delete entry. No entry in the system");

    @Getter
    private final String code;
    @Getter
    private final String ruText;
    @Getter
    private final String enText;

    public static Error findByCode(String code) {
        return Stream.of(values())
                .filter(error -> error.getCode().equals(code))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(MessageFormat.format("Error with code {0} not found", code)));
    }
}
