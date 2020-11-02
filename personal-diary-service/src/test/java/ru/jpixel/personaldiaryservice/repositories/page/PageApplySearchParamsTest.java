package ru.jpixel.personaldiaryservice.repositories.page;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import ru.jpixel.models.dtos.common.SearchParams;
import ru.jpixel.personaldiaryservice.domain.open.Diary;
import ru.jpixel.personaldiaryservice.domain.open.Page;
import ru.jpixel.personaldiaryservice.domain.open.Tag;
import ru.jpixel.personaldiaryservice.repositories.BaseRepositoryTest;
import ru.jpixel.personaldiaryservice.repositories.open.DiaryRepository;
import ru.jpixel.personaldiaryservice.repositories.open.PageRepository;
import ru.jpixel.personaldiaryservice.repositories.open.TagRepository;
import ru.jpixel.personaldiaryservice.services.PageApplySearchParams;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PageApplySearchParamsTest extends BaseRepositoryTest {

    @Autowired
    private PageApplySearchParams pageApplySearchParams;
    @Autowired
    private PageRepository pageRepository;
    @Autowired
    private DiaryRepository diaryRepository;
    @Autowired
    private TagRepository tagRepository;

    private Diary diary;
    private Tag firstTag;
    private Tag secondTag;
    private Page firstPage;
    private Page secondPage;
    private Page thirdPage;

    @Test
    @DisplayName("Применение фильтров")
    public void applySearchParamsTest() {
        preparedTags();
        preparedDiary();
        preparedPages();

        var searchParamsCaseOne = preparedSearchParamsCaseOne();

        var pageRequestCaseOne = PageRequest.of(searchParamsCaseOne.getPageNumber(), searchParamsCaseOne.getPageSize(), Sort.unsorted());
        var filterCaseOne = pageApplySearchParams.getSpecificationFilter(searchParamsCaseOne.getAdditionalFilter());
        var resultCaseOne = pageApplySearchParams.getSpecificationSort(filterCaseOne, searchParamsCaseOne.getOrderParameters());
        var pagesCaseOne = pageRepository.findAll(resultCaseOne, pageRequestCaseOne);

        assertEquals(2, pagesCaseOne.getContent().size());

        var contentCaseOne = pagesCaseOne.getContent();

        assertEquals(secondPage.getId(), contentCaseOne.get(0).getId());
        assertEquals(firstPage.getId(), contentCaseOne.get(1).getId());

        var searchParamsCaseTwo = preparedSearchParamsCaseTwo();

        var pageRequestCaseTwo = PageRequest.of(searchParamsCaseTwo.getPageNumber(), searchParamsCaseTwo.getPageSize(), Sort.unsorted());
        var filterCaseTwo = pageApplySearchParams.getSpecificationFilter(searchParamsCaseTwo.getAdditionalFilter());
        var resultCaseTwo = pageApplySearchParams.getSpecificationSort(filterCaseTwo, searchParamsCaseTwo.getOrderParameters());
        var pagesCaseTwo = pageRepository.findAll(resultCaseTwo, pageRequestCaseTwo);

        assertEquals(1, pagesCaseTwo.getContent().size());

        var contentCaseTwo = pagesCaseTwo.getContent();

        assertEquals(secondPage.getId(), contentCaseTwo.get(0).getId());


        var searchParamsCaseThree = preparedSearchParamsCaseThree();

        var pageRequestCaseThree = PageRequest.of(searchParamsCaseThree.getPageNumber(), searchParamsCaseThree.getPageSize(), Sort.unsorted());
        var filterCaseThree = pageApplySearchParams.getSpecificationFilter(searchParamsCaseThree.getAdditionalFilter());
        var resultCaseThree = pageApplySearchParams.getSpecificationSort(filterCaseThree, searchParamsCaseThree.getOrderParameters());
        var pagesCaseThree = pageRepository.findAll(resultCaseThree, pageRequestCaseThree);

        assertEquals(1, pagesCaseThree.getContent().size());

        var contentCaseThree = pagesCaseThree.getContent();

        assertEquals(thirdPage.getId(), contentCaseThree.get(0).getId());
    }

    private SearchParams preparedSearchParamsCaseOne() {
        var filterList = List.of(new SearchParams.Filter("FIND_BY_TAG", "101", SearchParams.Filter.DataType.STRING));
        var orderParameters = List.of(new SearchParams.OrderParameter("SORT_BY_CREATE_DATE", "desc"));
        return new SearchParams(0, 10, filterList, orderParameters);
    }

    private SearchParams preparedSearchParamsCaseTwo() {
        var filterList = List.of(new SearchParams.Filter("FIND_BY_CONFIDENTIAL", "true", SearchParams.Filter.DataType.BOOLEAN));
        return new SearchParams(0, 10, filterList, Collections.emptyList());
    }

    private SearchParams preparedSearchParamsCaseThree() {
        var filterList = List.of(new SearchParams.Filter("FIND_BY_CREATE_DATE", "20.10.2020", SearchParams.Filter.DataType.DATE));
        return new SearchParams(0, 10, filterList, Collections.emptyList());
    }

    private void preparedTags() {
        firstTag = new Tag();
        firstTag.setId(101L);
        firstTag.setNameRu("Заметка");
        firstTag.setNameEn("Note");
        firstTag.setCode("101");

        testEntityManager.persist(firstTag);

        secondTag = new Tag();
        secondTag.setId(102L);
        secondTag.setNameRu("Уведомление");
        secondTag.setNameEn("Notification");
        secondTag.setCode("102");

        testEntityManager.persist(secondTag);
    }

    private void preparedDiary() {
        diary = new Diary();
        diary.setUserId(2L);
        diary.setKey(new byte[]{});
        testEntityManager.persist(diary);
    }

    private void preparedPages() {
        firstPage = new Page();
        firstPage.setDiary(diaryRepository.findById(diary.getId()).orElseThrow());
        firstPage.setContent("Моя тествоая первая запись");
        firstPage.setRecordingSummary("Первая тестовая запись");
        firstPage.setTag(tagRepository.findById(firstTag.getId()).orElseThrow());
        firstPage.setCreateDate(LocalDateTime.of(LocalDate.of(2020, 10, 1), LocalTime.MAX));
        testEntityManager.persist(firstPage);

        secondPage = new Page();
        secondPage.setDiary(diaryRepository.findById(diary.getId()).orElseThrow());
        secondPage.setContent("Моя тествоая вторая запись");
        secondPage.setRecordingSummary("Вторая тестовая запись");
        secondPage.setTag(tagRepository.findById(firstTag.getId()).orElseThrow());
        secondPage.setCreateDate(LocalDateTime.of(LocalDate.of(2020, 10, 12), LocalTime.MAX));
        secondPage.setConfidential(true);
        testEntityManager.persist(secondPage);

        thirdPage = new Page();
        thirdPage.setDiary(diaryRepository.findById(diary.getId()).orElseThrow());
        thirdPage.setContent("Моя тествоая третья запись");
        thirdPage.setRecordingSummary("Третья тестовая запись");
        thirdPage.setTag(tagRepository.findById(secondTag.getId()).orElseThrow());
        thirdPage.setCreateDate(LocalDateTime.of(LocalDate.of(2020, 10, 20), LocalTime.MAX));
        thirdPage.setNotificationDate(LocalDate.now());
        testEntityManager.persist(thirdPage);
    }
}
