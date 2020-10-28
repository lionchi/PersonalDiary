package ru.jpixel.personaldiaryservice.dtos;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.jpixel.models.dtos.open.PageDto;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PageAllResponse {
    @ApiModelProperty(notes = "Общее количество записей на сервере")
    private Long totalCount;
    @ApiModelProperty(notes = "Список страниц")
    private List<PageDto> pages = new ArrayList<>();
}
