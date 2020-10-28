package ru.jpixel.models.dtos.open;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class PageDto {
    @ApiModelProperty(notes = "Идентификатор страницы")
    private Long id;
    @ApiModelProperty(notes = "Идентификатор дневника")
    private Long diaryId;
    @ApiModelProperty(notes = "Содержимое страницы")
    private String content;
    @ApiModelProperty(notes = "Тег страницы")
    private DirectoryDto tag;
    @ApiModelProperty(notes = "Дата уведомления")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    private LocalDate notificationDate;
    @ApiModelProperty(notes = "Дата создания")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy HH:mm:ss")
    private LocalDateTime createDate;
    @ApiModelProperty(notes = "Краткое описание содержимого страницы")
    private String recordingSummary;
    @ApiModelProperty(notes = "Флажок конфиденциальности данных")
    private boolean confidential;
}
