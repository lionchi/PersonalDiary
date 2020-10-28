package ru.jpixel.models.dtos.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.text.MessageFormat;

@NoArgsConstructor
@Getter
@Setter
public class OperationResult {

    @ApiModelProperty(notes = "Код результата выполненной операции")
    private String code;

    @ApiModelProperty(notes = "Руссикй текст результата выполненной операции")
    private String ruText;

    @ApiModelProperty(notes = "Английский текст результата выполненной операции")
    private String enText;

    @ApiModelProperty(notes = "Тип результата выполненной операции")
    private String resultType;

    @ApiModelProperty(notes = "Json для внутреннего взаимодействия")
    private String json;

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
