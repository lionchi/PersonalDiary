package ru.jpixel.personaldiaryservice.services;

import ru.jpixel.models.dtos.common.OperationResult;
import ru.jpixel.models.dtos.common.SearchParams;
import ru.jpixel.models.dtos.open.DirectoryDto;
import ru.jpixel.models.dtos.open.PageDto;
import ru.jpixel.models.dtos.open.StatisticsData;
import ru.jpixel.personaldiaryservice.dtos.PageAllResponse;

import java.util.List;

public interface DiaryService {
    OperationResult create(Long userId);

    OperationResult delete(Long diaryId);

    Long findDiaryIdByUserId(Long userId);

    List<DirectoryDto> downloadTags();

    OperationResult createPage(PageDto pageDto);

    OperationResult updatePage(PageDto pageDto);

    PageDto getPageById(Long pageId);

    OperationResult deletePage(Long pageId);

    PageAllResponse getPageAll(SearchParams searchParams);

    List<Long> findUserIds();

    StatisticsData getStatistics(Long diaryId);
}
