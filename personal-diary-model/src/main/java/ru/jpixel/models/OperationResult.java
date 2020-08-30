package ru.jpixel.models;

import lombok.Getter;
import lombok.Setter;

import java.text.MessageFormat;

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

    public OperationResult(Success success, String... arg) {
        this.code = success.getCode();
        this.ruText = MessageFormat.format(success.getRuText(), (Object[]) arg);
        this.enText = MessageFormat.format(success.getEnText(), (Object[]) arg);;
        this.resultType = ResultType.SUCCESS.getType();
    }

    public OperationResult(Error error, String... arg) {
        this.code = error.getCode();
        this.ruText = MessageFormat.format(error.getRuText(), (Object[]) arg);
        this.enText = MessageFormat.format(error.getEnText(), (Object[]) arg);;
        this.resultType = ResultType.ERROR.getType();
    }
}
