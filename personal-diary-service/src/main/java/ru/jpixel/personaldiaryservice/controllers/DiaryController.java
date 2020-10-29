package ru.jpixel.personaldiaryservice.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.Authorization;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.jpixel.models.dtos.common.OperationResult;
import ru.jpixel.models.dtos.common.SearchParams;
import ru.jpixel.models.dtos.open.DirectoryDto;
import ru.jpixel.models.dtos.open.PageDto;
import ru.jpixel.personaldiaryservice.dtos.PageAllResponse;
import ru.jpixel.personaldiaryservice.services.DiaryService;

import java.util.List;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
@Api(tags = "Diary API")
public class DiaryController {

    private final DiaryService diaryService;

    @PostMapping("create")
    @ApiOperation(value = "Создать дневник для пользоваля", response = OperationResult.class)
    public OperationResult create(@ApiParam(value = "Идентификатор пользователя для которого будет создан дневник", required = true)
                                  @RequestParam Long userId) {
        return diaryService.create(userId);
    }

    @GetMapping("findDiaryId")
    @ApiOperation(value = "Найти идентификатор дневника для пользователя по его идентификатору", response = Long.class)
    public Long findDiaryIdByUserId(@ApiParam(value = "Идентификатор пользователя для которого будет найден дневник")
                                    @RequestParam Long userId) {
        return diaryService.findDiaryIdByUserId(userId);
    }

    @GetMapping("download/tags")
    @ApiOperation(value = "Загрузить все теги для страницы в дневнике", response = List.class)
    public List<DirectoryDto> downloadTags() {
        return diaryService.downloadTags();
    }

    @PostMapping("page/create")
    @ApiOperation(value = "Создать новую страницу в дневнике", response = OperationResult.class)
    public OperationResult createPage(@RequestBody PageDto pageDto) {
        return diaryService.createPage(pageDto);
    }

    @PutMapping("page/update")
    @ApiOperation(value = "Обновить страницу в дневнике", response = OperationResult.class)
    public OperationResult updatePage(@RequestBody PageDto pageDto) {
        return diaryService.updatePage(pageDto);
    }

    @PostMapping("page/getAll")
    @ApiOperation(value = "Загрузить страницы дневника", response = PageAllResponse.class)
    public PageAllResponse getPageAll(@RequestBody SearchParams searchParams) {
        return diaryService.getPageAll(searchParams);
    }

    @GetMapping("page/get/{pageId}")
    @ApiOperation(value = "Получить страницу по идентификатору", response = PageDto.class)
    public PageDto getPageById(@PathVariable Long pageId) {
        return diaryService.getPageById(pageId);
    }

    @DeleteMapping("page/delete/{pageId}")
    @ApiOperation(value = "Удалить страницу из дневника", response = OperationResult.class)
    public OperationResult deletePage(@PathVariable Long pageId) {
        return diaryService.deletePage(pageId);
    }
}
