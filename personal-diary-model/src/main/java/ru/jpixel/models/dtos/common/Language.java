package ru.jpixel.models.dtos.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.text.MessageFormat;
import java.util.stream.Stream;

@RequiredArgsConstructor
public enum Language {
    RUSSIAN("ru"),
    ENGLAND("en");

    @Getter
    private final String code;

    public static Language findByCode(String code) {
        return Stream.of(values())
                .filter(language -> language.getCode().equals(code))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(MessageFormat.format("Language with code {0} not found", code)));
    }
}
