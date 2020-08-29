package ru.jpixel.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ResultType {
    SUCCESS("success"),
    INFO("info"),
    ERROR("error"),
    WARNING("warning");

    @Getter
    private final String type;
}
