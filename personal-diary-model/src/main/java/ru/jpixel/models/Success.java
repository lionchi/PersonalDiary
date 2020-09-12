package ru.jpixel.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.text.MessageFormat;
import java.util.stream.Stream;

@RequiredArgsConstructor
public enum Success {
    REGISTRATION("code.success.user_registration", "Ваша регистрация прошла успешно", "Your registration was successful"),

    PASSWORD_RESET_TOKEN_CREATE("code.success.password_reset_token_create", "Токен для сброса пароля создан", "Password reset token created"),

    RECOVERY_PASSWORD("code.success.recovery_password", "Пароль успешно изменен", "Password changed successfully");

    @Getter
    private final String code;
    @Getter
    private final String ruText;
    @Getter
    private final String enText;

    public static Success findByCode(String code) {
        return Stream.of(values())
                .filter(resultType -> resultType.getCode().equals(code))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(MessageFormat.format("Success with code {0} not found", code)));
    }
}
