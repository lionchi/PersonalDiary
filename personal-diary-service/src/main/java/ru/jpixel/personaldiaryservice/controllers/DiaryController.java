package ru.jpixel.personaldiaryservice.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.jpixel.models.dtos.common.OperationResult;
import ru.jpixel.models.dtos.common.SearchParams;
import ru.jpixel.models.dtos.open.DirectoryDto;
import ru.jpixel.models.dtos.open.PageDto;
import ru.jpixel.personaldiaryservice.services.DiaryService;

import java.util.List;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class DiaryController {

    private final DiaryService diaryService;

    @PostMapping("create")
    public OperationResult create(@RequestParam Long userId) {
        return diaryService.create(userId);
    }

    @GetMapping("findDiaryId")
    public Long findDiaryIdByUserId(@RequestParam Long userId) {
        return diaryService.findDiaryIdByUserId(userId);
    }

    @GetMapping("download/tags")
    public List<DirectoryDto> downloadTags() {
        return diaryService.downloadTags();
    }

    @PostMapping("page/create")
    public OperationResult createPage(@RequestBody PageDto pageDto) {
        return diaryService.createPage(pageDto);
    }

    @PutMapping("page/update")
    public OperationResult updatePage(@RequestBody PageDto pageDto) {
        return diaryService.updatePage(pageDto);
    }

    @GetMapping("page/get/totalCount/{diaryId}")
    public Integer getPageTotalCount(@PathVariable Long diaryId) {
        return diaryService.getPageTotalCount(diaryId);
    }

    @PostMapping("page/getAll")
    public List<PageDto> getPageAll(@RequestBody SearchParams searchParams) {
        return diaryService.getPageAll(searchParams);
    }

    @GetMapping("page/get/{pageId}")
    public PageDto getPageById(@PathVariable Long pageId) {
        return diaryService.getPageById(pageId);
    }

    @DeleteMapping("page/delete/{pageId}")
    public OperationResult deletePage(@PathVariable Long pageId) {
        return diaryService.deletePage(pageId);
    }
}
