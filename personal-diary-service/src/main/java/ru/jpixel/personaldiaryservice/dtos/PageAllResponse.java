package ru.jpixel.personaldiaryservice.dtos;

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
    private Long totalCount;
    private List<PageDto> pages = new ArrayList<>();
}
