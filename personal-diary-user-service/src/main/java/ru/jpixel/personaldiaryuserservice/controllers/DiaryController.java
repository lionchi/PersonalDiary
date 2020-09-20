package ru.jpixel.personaldiaryuserservice.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.jpixel.models.dtos.common.OperationResult;
import ru.jpixel.personaldiaryuserservice.services.DiaryService;

@RestController
@RequestMapping("/diary")
@RequiredArgsConstructor
public class DiaryController {

    private final DiaryService diaryService;

    @PostMapping("create")
    public OperationResult create(@RequestParam Long userId) {
        return diaryService.create(userId);
    }
}
