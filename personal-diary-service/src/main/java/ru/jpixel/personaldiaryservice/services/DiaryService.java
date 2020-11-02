package ru.jpixel.personaldiaryservice.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.jpixel.models.dtos.common.Error;
import ru.jpixel.models.dtos.common.OperationResult;
import ru.jpixel.models.dtos.common.SearchParams;
import ru.jpixel.models.dtos.common.Success;
import ru.jpixel.models.dtos.open.DirectoryDto;
import ru.jpixel.models.dtos.open.PageDto;
import ru.jpixel.personaldiaryservice.domain.open.Diary;
import ru.jpixel.personaldiaryservice.domain.open.Page;
import ru.jpixel.personaldiaryservice.dtos.PageAllResponse;
import ru.jpixel.personaldiaryservice.exceptions.CryptoFacadeException;
import ru.jpixel.personaldiaryservice.facades.CryptoFacade;
import ru.jpixel.personaldiaryservice.repositories.open.DiaryRepository;
import ru.jpixel.personaldiaryservice.repositories.open.PageRepository;
import ru.jpixel.personaldiaryservice.repositories.open.TagRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@Service
@RequiredArgsConstructor
public class DiaryService {

    private final DiaryRepository diaryRepository;
    private final TagRepository tagRepository;
    private final PageRepository pageRepository;
    private final PageApplySearchParams pageApplySearchParams;

    @Value("${encryption.key.algorithm}")
    private String algorithmKey;
    @Value("${encryption.key.charset}")
    private String nameCharset;
    @Value("${encryption.algorithm}")
    private String algorithmCipher;

    /**
     * Создает дневник для пользователя
     *
     * @param userId идентификатор пользователя
     * @return результат операции
     */
    @Transactional
    public OperationResult create(Long userId) {
        var diary = new Diary();
        diary.setUserId(userId);
        try {
            var cryptoFacade = new CryptoFacade(algorithmKey, nameCharset, "");
            diary.setKey(cryptoFacade.keyGeneration());
        } catch (CryptoFacadeException e) {
            log.error("Неудачная попытка генерации ключа", e);
            return new OperationResult(Error.NOT_CREATE_DIARY);
        }
        diaryRepository.save(diary);
        var operationResult = new OperationResult(Success.CREATE_DIARY);
        operationResult.setJson(String.valueOf(diary.getId()));
        return operationResult;
    }

    /**
     * Получает индентификатор дневника по иднетификатору пользователя
     *
     * @param userId идентификатор пользователя
     * @return идентификатор дневника
     */
    public Long findDiaryIdByUserId(Long userId) {
        return diaryRepository.findByUserId(userId);
    }

    /**
     * Загружает на клиента теги
     *
     * @return список тегов
     */
    public List<DirectoryDto> downloadTags() {
        var directoryConverter = new DirectoryConverter();
        return StreamSupport.stream(tagRepository.findAll().spliterator(), false)
                .map(directoryConverter::convert)
                .collect(Collectors.toList());
    }

    /**
     * Создает запись в дневнике
     *
     * @param pageDto объект из интерфейса
     * @return результат операции
     */
    @Transactional
    public OperationResult createPage(PageDto pageDto) {
        var page = new Page();
        var diary = diaryRepository.findById(pageDto.getDiaryId()).orElse(null);
        if (diary == null) {
            return new OperationResult(Error.NOT_CREATE_PAGE_DIARY_ID);
        }
        page.setDiary(diary);
        page.setCreateDate(LocalDateTime.now());
        page.setRecordingSummary(pageDto.getRecordingSummary());
        page.setNotificationDate(pageDto.getNotificationDate());
        page.setConfidential(pageDto.isConfidential());
        page.setTag(tagRepository.findByCode(pageDto.getTag().getCode()));
        return contentEncryption(page, pageDto.getContent(), diary.getKey(), Success.CREATE_PAGE, Error.NOT_CREATE_PAGE);
    }

    /**
     * Обновляет запись в дневника
     *
     * @param pageDto объект из интерфейса
     * @return результат операции
     */
    @Transactional
    public OperationResult updatePage(PageDto pageDto) {
        var page = pageRepository.findById(pageDto.getId()).orElse(null);
        if (page == null) {
            return new OperationResult(Error.NOT_EDIT_PAGE_ID);
        }
        var diary = page.getDiary();
        page.setRecordingSummary(pageDto.getRecordingSummary());
        page.setNotificationDate(pageDto.getNotificationDate());
        page.setConfidential(pageDto.isConfidential());
        page.setTag(tagRepository.findByCode(pageDto.getTag().getCode()));
        return contentEncryption(page, pageDto.getContent(), diary.getKey(), Success.EDIT_PAGE, Error.NOT_EDIT_PAGE);
    }

    private OperationResult contentEncryption(Page page, String content, byte[] key, Success success, Error error) {
        if (page.isConfidential()) {
            try {
                var cryptoFacade = new CryptoFacade("", nameCharset, algorithmCipher);
                page.setContent(cryptoFacade.encryptContent(key, content.getBytes()));
            } catch (CryptoFacadeException e) {
                log.error("Неудачная попытка шифрования данных", e);
                return new OperationResult(error);
            }
        } else {
            page.setContent(content);
        }
        pageRepository.save(page);
        return new OperationResult(success);
    }

    /**
     * Получает запись дневника
     *
     * @param pageId иднетификатор записи
     * @return запись дневника
     */
    @Transactional
    public PageDto getPageById(Long pageId) {
        var page = pageRepository.findById(pageId).orElse(null);
        if (page == null) {
            return null;
        }
        var result = new PageConverter().convert(page);
        var diary = page.getDiary();
        result.setDiaryId(diary.getId());
        if (page.isConfidential()) {
            try {
                var cryptoFacade = new CryptoFacade("", nameCharset, algorithmCipher);
                result.setContent(cryptoFacade.decryptContent(diary.getKey(), page.getContent()));
            } catch (CryptoFacadeException e) {
                log.error("Неудачная попытка дешифрования данных", e);
            }
        }
        return result;
    }

    /**
     * Удаляет запись из дненивка
     *
     * @param pageId идентификатор записи
     * @return результат операции
     */
    @Transactional
    public OperationResult deletePage(Long pageId) {
        try {
            pageRepository.deleteById(pageId);
        } catch (EmptyResultDataAccessException e) {
            return new OperationResult(Error.NOT_DELETE_PAGE);
        }
        return new OperationResult(Success.DELETE_PAGE);
    }

    /**
     * Получает записи дненивка
     *
     * @param searchParams параметры поиска
     * @return записи дневника
     */
    public PageAllResponse getPageAll(SearchParams searchParams) {
        var pageRequest = PageRequest.of(searchParams.getPageNumber(), searchParams.getPageSize(), Sort.unsorted());
        var filter = pageApplySearchParams.getSpecificationFilter(searchParams.getAdditionalFilter());
        var result = pageApplySearchParams.getSpecificationSort(filter, searchParams.getOrderParameters());
        var pages = pageRepository.findAll(result, pageRequest);
        var response = new PageAllResponse();
        response.setTotalCount(pages.getTotalElements());
        response.setPages(pages.getContent().stream()
                .map(page -> new PageConverter().convert(page))
                .collect(Collectors.toList()));
        return response;
    }
}
