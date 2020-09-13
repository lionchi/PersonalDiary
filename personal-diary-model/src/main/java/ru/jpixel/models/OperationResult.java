package ru.jpixel.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.text.MessageFormat;

@NoArgsConstructor
@Getter
@Setter
public class OperationResult {

    private String code;

    private String ruText;

    private String enText;

    private String resultType;

    private Object payload;

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

    public ResultType getResultTypeEnum() {
        return ResultType.findByType(resultType);
    }
}
