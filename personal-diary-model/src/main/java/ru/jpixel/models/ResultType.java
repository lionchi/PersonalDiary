package ru.jpixel.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.text.MessageFormat;
import java.util.stream.Stream;

@RequiredArgsConstructor
public enum ResultType {
    SUCCESS("success"),
    INFO("info"),
    ERROR("error"),
    WARNING("warning");

    @Getter
    private final String type;

    public static ResultType findByType(String type) {
        return Stream.of(values())
                .filter(resultType -> resultType.getType().equals(type))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(MessageFormat.format("ResultType with code {0} not found", type)));
    }
}
