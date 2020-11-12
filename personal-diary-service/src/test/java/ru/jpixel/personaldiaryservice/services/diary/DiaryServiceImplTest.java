package ru.jpixel.personaldiaryservice.services.diary;

import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import ru.jpixel.models.dtos.common.Error;
import ru.jpixel.models.dtos.common.SearchParams;
import ru.jpixel.models.dtos.common.Success;
import ru.jpixel.models.dtos.open.PageDto;
import ru.jpixel.models.dtos.open.TagDto;
import ru.jpixel.personaldiaryservice.domain.open.Diary;
import ru.jpixel.personaldiaryservice.domain.open.Page;
import ru.jpixel.personaldiaryservice.domain.open.Tag;
import ru.jpixel.personaldiaryservice.exceptions.CryptoFacadeException;
import ru.jpixel.personaldiaryservice.facades.CryptoFacade;
import ru.jpixel.personaldiaryservice.repositories.open.DiaryRepository;
import ru.jpixel.personaldiaryservice.repositories.open.PageRepository;
import ru.jpixel.personaldiaryservice.repositories.open.TagRepository;
import ru.jpixel.personaldiaryservice.services.BaseServiceTest;
import ru.jpixel.personaldiaryservice.services.DiaryService;
import ru.jpixel.personaldiaryservice.services.PageApplySearchParams;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;

public class DiaryServiceImplTest {

    private static class InnerClass extends BaseServiceTest {
        @Value("${encryption.key.algorithm}")
        protected String algorithmKey;
        @Value("${encryption.key.charset}")
        protected String nameCharset;
        @Value("${encryption.algorithm}")
        protected String algorithmCipher;

        @MockBean
        protected DiaryRepository diaryRepository;
        @MockBean
        protected TagRepository tagRepository;
        @MockBean
        protected PageRepository pageRepository;
        @MockBean
        protected PageApplySearchParams pageApplySearchParams;
        @Autowired
        protected DiaryService diaryService;
    }


    @Nested
    @DisplayName("Проверка работы метода create")
    public class CreateDiary extends InnerClass {
        @Test
        public void createTest() {
            var userId = 1L;

            var operationResult = assertDoesNotThrow(() -> diaryService.create(userId));

            Mockito.verify(diaryRepository).save(any(Diary.class));

            assertEquals(Success.CREATE_DIARY.getCode(), operationResult.getCode());
        }
    }

    @Nested
    @DisplayName("Проверка работы метода delete")
    public class DeleteDiary extends InnerClass {
        @Test
        public void deleteSuccessTest() {
            var diaryId = 1L;

            var operationResult = assertDoesNotThrow(() -> diaryService.delete(diaryId));

            Mockito.verify(diaryRepository).deleteById(anyLong());

            assertEquals(Success.DELETE_DIARY.getCode(), operationResult.getCode());

        }

        @Test
        public void deleteailedTest() {
            var diaryId = 1L;

            Mockito.doThrow(EmptyResultDataAccessException.class).when(diaryRepository).deleteById(anyLong());

            var operationResult = assertDoesNotThrow(() -> diaryService.delete(diaryId));

            Mockito.verify(diaryRepository).deleteById(anyLong());

            assertEquals(Error.NOT_DELETE_DIARY.getCode(), operationResult.getCode());

        }
    }

    @Nested
    @DisplayName("Проверка работы метода findDiaryIdByUserId")
    public class FindDiaryIdByUserId extends InnerClass {
        @Test
        public void findDiaryIdByUserIdTest() {
            var userId = 1L;

            Mockito.when(diaryRepository.findByUserId(userId))
                    .thenReturn(1L);

            assertDoesNotThrow(() -> diaryService.findDiaryIdByUserId(userId));

            Mockito.verify(diaryRepository).findByUserId(anyLong());
        }
    }

    @Nested
    @DisplayName("Проверка работы метода downloadTags")
    public class DownloadTags extends InnerClass {
        @Test
        public void downloadTagsTest() {
            Mockito.when(tagRepository.findAll())
                    .thenReturn(Collections.emptyList());

            assertDoesNotThrow(() -> diaryService.downloadTags());

            Mockito.verify(tagRepository).findAll();
        }
    }

    @Nested
    @DisplayName("Проверка работы метода createPage")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    public class CreatePage extends InnerClass {

        private PageDto pageDto;
        private CryptoFacade cryptoFacade;

        @BeforeAll
        public void init() {
            var tagDto = new TagDto();
            tagDto.setId(1L);
            tagDto.setNameRu("Заметка");
            tagDto.setNameEn("Note");
            tagDto.setCode("1");

            pageDto = new PageDto();
            pageDto.setTag(tagDto);
            pageDto.setCreateDate(LocalDateTime.now());
            pageDto.setDiaryId(1L);
            pageDto.setContent("ТекстТекстТекстТекстТекст");
            pageDto.setRecordingSummary("Первая запись");

            cryptoFacade = new CryptoFacade(algorithmKey, nameCharset, algorithmCipher);
        }

        @Test
        public void createPageFailedFirstCaseTest() {
            Mockito.when(diaryRepository.findById(pageDto.getDiaryId()))
                    .thenReturn(Optional.empty());

            var operationResult = diaryService.createPage(pageDto);

            assertEquals(Error.NOT_CREATE_PAGE_DIARY_ID.getCode(), operationResult.getCode());
        }

        @Test
        public void createPageFailedSecondCaseTest() {
            pageDto.setConfidential(true);

            var diary = new Diary();
            diary.setId(1L);
            diary.setUserId(1L);
            diary.setKey(new byte[]{});

            Mockito.when(diaryRepository.findById(pageDto.getDiaryId()))
                    .thenReturn(Optional.of(diary));

            Mockito.when(tagRepository.findByCode(pageDto.getTag().getCode()))
                    .thenReturn(new Tag());

            var operationResult = assertDoesNotThrow(() -> diaryService.createPage(pageDto));

            assertEquals(Error.NOT_CREATE_PAGE.getCode(), operationResult.getCode());
        }

        @Test
        public void createPageSuccessFirstCaseTest() {
            pageDto.setConfidential(false);

            Mockito.when(diaryRepository.findById(pageDto.getDiaryId()))
                    .thenReturn(Optional.of(new Diary()));

            Mockito.when(tagRepository.findByCode(pageDto.getTag().getCode()))
                    .thenReturn(new Tag());

            var operationResult = diaryService.createPage(pageDto);

            Mockito.verify(pageRepository).save(any(Page.class));

            assertEquals(Success.CREATE_PAGE.getCode(), operationResult.getCode());
        }

        @Test
        public void createPageSuccessSecondCaseTest() throws CryptoFacadeException {
            pageDto.setConfidential(true);

            var diary = new Diary();
            diary.setId(1L);
            diary.setUserId(1L);
            diary.setKey(cryptoFacade.keyGeneration());

            Mockito.when(diaryRepository.findById(pageDto.getDiaryId()))
                    .thenReturn(Optional.of(diary));

            Mockito.when(tagRepository.findByCode(pageDto.getTag().getCode()))
                    .thenReturn(new Tag());

            var operationResult = diaryService.createPage(pageDto);

            Mockito.verify(pageRepository).save(any(Page.class));

            assertEquals(Success.CREATE_PAGE.getCode(), operationResult.getCode());
        }
    }

    @Nested
    @DisplayName("Проверка работы метода updatePage")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    public class UpdatePage extends InnerClass {

        private Page page;
        private PageDto pageDto;
        private CryptoFacade cryptoFacade;

        @BeforeAll
        public void init() {
            var tag = new Tag();
            tag.setId(1L);
            tag.setNameRu("Заметка");
            tag.setNameEn("Note");
            tag.setCode("1");

            var diary = new Diary();
            diary.setId(1L);
            diary.setUserId(1L);
            diary.setKey(new byte[]{});

            page = new Page();
            page.setId(1L);
            page.setTag(tag);
            page.setCreateDate(LocalDateTime.of(LocalDate.of(2020, 11, 1), LocalTime.MAX));
            page.setContent("Текст1Текст1Текст1Текст1Текст");
            page.setRecordingSummary("Первая запись");
            page.setDiary(diary);

            var tagDto = new TagDto();
            tagDto.setId(2L);
            tagDto.setNameRu("Уведомление");
            tagDto.setNameEn("Notification");
            tagDto.setCode("2");

            pageDto = new PageDto();
            pageDto.setId(1L);
            pageDto.setTag(tagDto);
            pageDto.setCreateDate(LocalDateTime.now());
            pageDto.setDiaryId(1L);
            pageDto.setContent("Текст2Текст2Текст2Текст2Текст");
            pageDto.setRecordingSummary("Первая запись");

            cryptoFacade = new CryptoFacade(algorithmKey, nameCharset, algorithmCipher);
        }

        @Test
        public void updatePageFailedFirstCaseTest() {
            Mockito.when(pageRepository.findById(page.getId()))
                    .thenReturn(Optional.empty());

            var operationResult = diaryService.updatePage(pageDto);

            assertEquals(Error.NOT_EDIT_PAGE_ID.getCode(), operationResult.getCode());
        }

        @Test
        public void updatePageFailedSecondCaseTest() {
            pageDto.setConfidential(true);

            Mockito.when(pageRepository.findById(page.getId()))
                    .thenReturn(Optional.of(page));

            Mockito.when(tagRepository.findByCode(pageDto.getTag().getCode()))
                    .thenReturn(new Tag());

            var operationResult = assertDoesNotThrow(() -> diaryService.updatePage(pageDto));

            assertEquals(Error.NOT_EDIT_PAGE.getCode(), operationResult.getCode());
        }

        @Test
        public void updatePageSuccessFirstCaseTest() {
            pageDto.setConfidential(false);

            Mockito.when(pageRepository.findById(page.getId()))
                    .thenReturn(Optional.of(page));

            Mockito.when(tagRepository.findByCode(pageDto.getTag().getCode()))
                    .thenReturn(new Tag());

            var operationResult = diaryService.updatePage(pageDto);

            Mockito.verify(pageRepository).save(any(Page.class));

            assertEquals(Success.EDIT_PAGE.getCode(), operationResult.getCode());
        }

        @Test
        public void updatePageSuccessSecondCaseTest() throws CryptoFacadeException {
            pageDto.setConfidential(true);

            page.getDiary().setKey(cryptoFacade.keyGeneration());

            Mockito.when(pageRepository.findById(page.getId()))
                    .thenReturn(Optional.of(page));

            Mockito.when(tagRepository.findByCode(pageDto.getTag().getCode()))
                    .thenReturn(new Tag());

            var operationResult = assertDoesNotThrow(() -> diaryService.updatePage(pageDto));

            Mockito.verify(pageRepository).save(any(Page.class));

            assertEquals(Success.EDIT_PAGE.getCode(), operationResult.getCode());
        }
    }

    @Nested
    @DisplayName("Проверка работы метода getPageById")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    public class GetPageById extends InnerClass {

        private Page page;
        private CryptoFacade cryptoFacade;

        @BeforeAll
        public void init() {
            var tag = new Tag();
            tag.setId(1L);
            tag.setNameRu("Заметка");
            tag.setNameEn("Note");
            tag.setCode("1");

            var diary = new Diary();
            diary.setId(1L);
            diary.setUserId(1L);
            diary.setKey(new byte[]{});

            page = new Page();
            page.setId(1L);
            page.setTag(tag);
            page.setCreateDate(LocalDateTime.of(LocalDate.of(2020, 11, 1), LocalTime.MAX));
            page.setContent("ТекстТекстТекстТекстТекст");
            page.setRecordingSummary("Первая запись");
            page.setDiary(diary);

            cryptoFacade = new CryptoFacade(algorithmKey, nameCharset, algorithmCipher);
        }

        @Test
        public void getPageByIdFirstCaseTest() {
            var pageId = 1L;

            Mockito.when(pageRepository.findById(page.getId()))
                    .thenReturn(Optional.empty());

            var pageDto = diaryService.getPageById(pageId);

            assertNull(pageDto);
        }

        @Test
        public void getPageByIdSecondCaseTest() throws CryptoFacadeException {
            var pageId = 1L;

            page.getDiary().setKey(cryptoFacade.keyGeneration());

            Mockito.when(pageRepository.findById(page.getId()))
                    .thenReturn(Optional.of(page));

            var pageDto = diaryService.getPageById(pageId);

            assertEquals(page.getId(), pageDto.getId());
        }
    }

    @Nested
    @DisplayName("Проверка работы метода deletePage")
    public class DeletePage extends InnerClass {
        @Test
        public void deletePageSuccessTest() {
            var pageId = 1L;

            var operationResult = assertDoesNotThrow(() -> diaryService.deletePage(pageId));

            Mockito.verify(pageRepository).deleteById(anyLong());

            assertEquals(Success.DELETE_PAGE.getCode(), operationResult.getCode());

        }

        @Test
        public void deletePageFailedTest() {
            var pageId = 1L;

            Mockito.doThrow(EmptyResultDataAccessException.class).when(pageRepository).deleteById(anyLong());

            var operationResult = assertDoesNotThrow(() -> diaryService.deletePage(pageId));

            Mockito.verify(pageRepository).deleteById(anyLong());

            assertEquals(Error.NOT_DELETE_PAGE.getCode(), operationResult.getCode());

        }
    }

    @Nested
    @DisplayName("Проверка работы метода getPageAll")
    public class GetPageAll extends InnerClass {
        @Test
        public void getPageAllTest() {
            var pageNumber = 0;
            var pageSize = 10;

            var pageRequest = PageRequest.of(pageNumber, pageSize, Sort.unsorted());
            var searchParams = new SearchParams(pageNumber, pageSize, Collections.emptyList(), Collections.emptyList());

            Specification<Page> stub = (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.isTrue(criteriaBuilder.literal(Boolean.TRUE));

            Mockito.when(pageApplySearchParams.getSpecificationFilter(anyList()))
                    .thenReturn(stub);

            Mockito.when(pageApplySearchParams.getSpecificationSort(any(), anyList()))
                    .thenReturn(stub);

            Mockito.when(pageRepository.findAll(stub, pageRequest))
                    .thenReturn(new PageImpl<>(Collections.emptyList()));

            assertDoesNotThrow(() -> diaryService.getPageAll(searchParams));
        }
    }

    @Nested
    @DisplayName("Проверка работы метода findUserIds")
    public class FindUserIds extends InnerClass {
        @Test
        public void findUserIdsTest() {
            Mockito.when(pageRepository.findUserIdsWithTagNotificationAndTagReminder(anyCollection(), any(LocalDate.class)))
                    .thenReturn(Collections.emptyList());

            assertDoesNotThrow(() -> diaryService.findUserIds());

            Mockito.verify(pageRepository).findUserIdsWithTagNotificationAndTagReminder(anyCollection(), any(LocalDate.class));
        }
    }
}
