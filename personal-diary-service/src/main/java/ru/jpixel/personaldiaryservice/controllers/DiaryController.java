package ru.jpixel.personaldiaryservice.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.jpixel.models.dtos.common.OperationResult;
import ru.jpixel.personaldiaryservice.services.DiaryService;

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
}
