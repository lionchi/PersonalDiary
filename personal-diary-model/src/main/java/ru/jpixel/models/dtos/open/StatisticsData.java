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
public class StatisticsData {
    @ApiModelProperty(notes = "Количество записей в дневнике")
    private long quantityPage;
    @ApiModelProperty(notes = "Количество записей в дневнике c флагом конфиденциально")
    private long quantityConfPage;
    @ApiModelProperty(notes = "Количество записей в дневнике без флага конфиденциально")
    private long quantityNonConfPage;
    @ApiModelProperty(notes = "Количество записей в дневнике c тегом 'Уведомление'")
    private long quantityNotificationPage;
    @ApiModelProperty(notes = "Количество записей в дневнике c тегом 'Напоминание'")
    private long quantityRemainderPage;
    @ApiModelProperty(notes = "Количество записей в дневнике c тегом 'Заметка'")
    private long quantityNotePage;
    @ApiModelProperty(notes = "Количество записей в дневнике c тегом 'Закладка'")
    private long quantityBookmarkPage;
    @ApiModelProperty(notes = "Дата последней записи в дневнике")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy HH:mm:ss")
    private LocalDateTime dateOfLastEntry;
    @ApiModelProperty(notes = "Дата ближайшего уведомления/напоминания")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dateOfNextNotificationAndReminder;
}
