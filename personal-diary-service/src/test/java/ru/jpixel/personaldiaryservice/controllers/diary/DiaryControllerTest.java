package ru.jpixel.personaldiaryservice.controllers.diary;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.jpixel.models.dtos.common.OperationResult;
import ru.jpixel.models.dtos.common.SearchParams;
import ru.jpixel.models.dtos.common.Success;
import ru.jpixel.models.dtos.open.DirectoryDto;
import ru.jpixel.models.dtos.open.PageDto;
import ru.jpixel.models.dtos.open.StatisticsData;
import ru.jpixel.personaldiaryservice.controllers.BaseControllerTest;
import ru.jpixel.personaldiaryservice.dtos.PageAllResponse;
import ru.jpixel.personaldiaryservice.services.DiaryService;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

public class DiaryControllerTest extends BaseControllerTest {

    @MockBean
    private DiaryService diaryService;

    @Test
    @DisplayName("diary api method create")
    public void createTest() throws Exception {
        var userId = 1L;

        var operationResult = new OperationResult(Success.CREATE_DIARY);

        Mockito.when(diaryService.create(userId)).thenReturn(operationResult);

        mockMvc.perform(MockMvcRequestBuilders.post("/create")
                .queryParam("userId", String.valueOf(userId))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(operationResult.getCode()));
    }

    @Test
    @DisplayName("diary api method delete")
    public void deleteTest() throws Exception {
        var diaryId = 1L;

        var operationResult = new OperationResult(Success.DELETE_DIARY);

        Mockito.when(diaryService.delete(diaryId)).thenReturn(operationResult);

        mockMvc.perform(MockMvcRequestBuilders.delete("/delete")
                .queryParam("diaryId", String.valueOf(diaryId))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(operationResult.getCode()));
    }

    @Test
    @DisplayName("diary api method findDiaryIdByUserId")
    public void findDiaryIdByUserIdTest() throws Exception {
        var returnId = 1L;
        var userId = 1L;

        Mockito.when(diaryService.findDiaryIdByUserId(userId)).thenReturn(returnId);

        mockMvc.perform(MockMvcRequestBuilders.get("/findDiaryId")
                .queryParam("userId", String.valueOf(userId))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(String.valueOf(returnId)));
    }

    @Test
    @DisplayName("diary api method downloadTags")
    public void downloadTagsTest() throws Exception {
        var firstTag = new DirectoryDto();
        firstTag.setId(1L);
        firstTag.setNameRu("Заметка");
        firstTag.setNameEn("Note");
        firstTag.setCode("1");

        var secondTag = new DirectoryDto();
        secondTag.setId(2L);
        secondTag.setNameRu("Уведомление");
        secondTag.setNameEn("Notification");
        secondTag.setCode("2");

        var tags = List.of(firstTag, secondTag);

        Mockito.when(diaryService.downloadTags()).thenReturn(tags);

        var actions = mockMvc.perform(MockMvcRequestBuilders.get("/download/tags")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        var response = asObject(actions.andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8), DirectoryDto[].class);

        assertEquals(tags.size(), response.length);
    }

    @Test
    @DisplayName("diary api method createPage")
    public void createPageTest() throws Exception {
        var operationResult = new OperationResult(Success.CREATE_PAGE);

        Mockito.when(diaryService.createPage(any(PageDto.class))).thenReturn(operationResult);

        mockMvc.perform(MockMvcRequestBuilders.post("/page/create")
                .content(asJsonString(new PageDto()))
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(operationResult.getCode()));
    }

    @Test
    @DisplayName("diary api method updatePage")
    public void updatePageTest() throws Exception {
        var operationResult = new OperationResult(Success.EDIT_PAGE);

        Mockito.when(diaryService.updatePage(any(PageDto.class))).thenReturn(operationResult);

        mockMvc.perform(MockMvcRequestBuilders.put("/page/update")
                .content(asJsonString(new PageDto()))
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(operationResult.getCode()));
    }

    @Test
    @DisplayName("diary api method getPageAll")
    public void getPageAllTest() throws Exception {
        var response = new PageAllResponse();
        response.setTotalCount(99999L);
        response.setPages(Collections.emptyList());

        Mockito.when(diaryService.getPageAll(any(SearchParams.class))).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders.post("/page/getAll")
                .content(asJsonString(new SearchParams()))
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalCount").value(response.getTotalCount()));
    }

    @Test
    @DisplayName("diary api method getPageById")
    public void getPageByIdTest() throws Exception {
        var pageId = 1L;

        var response = new PageDto();
        response.setId(pageId);

        Mockito.when(diaryService.getPageById(pageId)).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders.get("/page/get/{pageId}", pageId)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(response.getId()));
    }

    @Test
    @DisplayName("diary api method deletePage")
    public void deletePageTest() throws Exception {
        var pageId = 1L;

        var operationResult = new OperationResult(Success.DELETE_PAGE);

        Mockito.when(diaryService.deletePage(pageId)).thenReturn(operationResult);

        mockMvc.perform(MockMvcRequestBuilders.delete("/page/delete/{pageId}", pageId)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(operationResult.getCode()));
    }

    @Test
    @DisplayName("diary api method findUserIds")
    public void findUserIdsTest() throws Exception {
        Mockito.when(diaryService.findUserIds()).thenReturn(Collections.emptyList());

        mockMvc.perform(MockMvcRequestBuilders.get("/findUserIds")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("diary api method statistics")
    public void statisticsTest() throws Exception {
        var diaryId = 1L;

        Mockito.when(diaryService.getStatistics(diaryId))
                .thenReturn(new StatisticsData());

        mockMvc.perform(MockMvcRequestBuilders.get("/statistics")
                .queryParam("diaryId", String.valueOf(diaryId))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
