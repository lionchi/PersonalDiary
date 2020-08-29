package ru.jpixel.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Success {
    REGISTRATION("code.success.user_registration", "Ваша регистрация прошла успешно", "Your registration was successful");

    @Getter
    private final String code;
    @Getter
    private final String ruText;
    @Getter
    private final String enText;
}
