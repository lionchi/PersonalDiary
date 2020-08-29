package ru.jpixel.models;

import lombok.Getter;
import lombok.Setter;

public class OperationResult {

    @Getter
    @Setter
    private String code;

    @Getter
    @Setter
    private String ruText;

    @Getter
    @Setter
    private String enText;

    @Getter
    @Setter
    private String resultType;

    public OperationResult(Success success) {
        this.code = success.getCode();
        this.ruText = success.getRuText();
        this.enText = success.getEnText();
        this.resultType = ResultType.SUCCESS.getType();
    }

    public OperationResult(Error error) {
        this.code = error.getCode();
        this.ruText = error.getRuText();
        this.enText = error.getEnText();
        this.resultType = ResultType.ERROR.getType();
    }
}
