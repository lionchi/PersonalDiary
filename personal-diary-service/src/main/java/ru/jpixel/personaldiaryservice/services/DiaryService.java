package ru.jpixel.personaldiaryservice.services;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.jpixel.models.dtos.common.Error;
import ru.jpixel.models.dtos.common.OperationResult;
import ru.jpixel.models.dtos.common.Success;
import ru.jpixel.models.dtos.open.DirectoryDto;
import ru.jpixel.models.dtos.open.PageDto;
import ru.jpixel.personaldiaryservice.domain.open.Diary;
import ru.jpixel.personaldiaryservice.domain.open.Page;
import ru.jpixel.personaldiaryservice.facades.GoogleGsonFacade;
import ru.jpixel.personaldiaryservice.repositories.open.DiaryRepository;
import ru.jpixel.personaldiaryservice.repositories.open.PageRepository;
import ru.jpixel.personaldiaryservice.repositories.open.TagRepository;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.xml.bind.DatatypeConverter;
import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class DiaryService {

    private final Logger logger = LoggerFactory.getLogger(DiaryService.class);

    private final DiaryRepository diaryRepository;
    private final TagRepository tagRepository;
    private final PageRepository pageRepository;

    @Value("${encryption.key.algorithm}")
    private String algorithmKey;
    @Value("${encryption.key.charset}")
    private String nameCharset;
    @Value("${encryption.algorithm}")
    private String algorithmCipher;

    @Transactional
    public OperationResult create(Long userId) {
        var diary = new Diary();
        diary.setUserId(userId);
        try {
            var keyGenerator = KeyGenerator.getInstance(algorithmKey);
            keyGenerator.init(256);
            var key = keyGenerator.generateKey();
            var keyJson = new GoogleGsonFacade<>(Key.class).toJson(key);
            var keyJsonByte = keyJson.getBytes(nameCharset);
            diary.setKey(keyJsonByte);
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            logger.error("Неудачная попытка генерации ключа", e);
            return new OperationResult(Error.NOT_CREATE_DIARY);
        }
        diaryRepository.save(diary);
        var operationResult = new OperationResult(Success.CREATE_DIARY);
        operationResult.setJson(String.valueOf(diary.getId()));
        return operationResult;
    }

    @Transactional
    public Long findDiaryIdByUserId(Long userId) {
        return diaryRepository.findByUserId(userId);
    }

    public List<DirectoryDto> downloadTags() {
        var directoryConverter = new DirectoryConverter();
        return StreamSupport.stream(tagRepository.findAll().spliterator(), false)
                .map(directoryConverter::convert)
                .collect(Collectors.toList());
    }

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
        if (page.isConfidential()) {
            try {
                Cipher cipher = Cipher.getInstance(algorithmCipher);
                Key keyOfJson = new GoogleGsonFacade<>(Key.class).fromJson(diary.getKey(), nameCharset);
                cipher.init(Cipher.ENCRYPT_MODE, keyOfJson);
                byte[] encrypted = cipher.doFinal(pageDto.getContent().getBytes());
                page.setContent(DatatypeConverter.printHexBinary(encrypted));
            } catch (Exception e) {
                logger.error("Неудачная попытка шифрования данных", e);
                return new OperationResult(Error.NOT_CREATE_PAGE);
            }
        } else {
            page.setContent(pageDto.getContent());
        }
        pageRepository.save(page);
        return new OperationResult(Success.CREATE_PAGE);
    }

    @Transactional
    public PageDto getPageById(Long pageId) {
        var page = pageRepository.findById(pageId).orElse(null);
        if (page == null) {
            return null;
        }
        var result = new PageDto();
        result.setId(page.getId());
        var diary = page.getDiary();
        result.setDiaryId(diary.getId());
        result.setRecordingSummary(page.getRecordingSummary());
        result.setNotificationDate(page.getNotificationDate());
        result.setCreateDate(page.getCreateDate());
        result.setTag(new DirectoryConverter().convert(page.getTag()));
        result.setConfidential(page.isConfidential());
        if (page.isConfidential()) {
            try {
                Cipher cipher = Cipher.getInstance(algorithmCipher);
                Key keyOfJson = new GoogleGsonFacade<>(Key.class).fromJson(diary.getKey(), nameCharset);
                cipher.init(Cipher.DECRYPT_MODE, keyOfJson);
                result.setContent(new String(cipher.doFinal(DatatypeConverter.parseHexBinary(page.getContent()))));
            } catch (Exception e) {
                logger.error("Неудачная попытка дешифрования данных", e);
            }
        } else {
            result.setContent(page.getContent());
        }
        return result;
    }

    @Transactional
    public OperationResult deletePage(Long pageId) {
        try {
            pageRepository.deleteById(pageId);
        } catch (EmptyResultDataAccessException e) {
            new OperationResult(Error.NOT_DELETE_PAGE);
        }
        return new OperationResult(Success.DELETE_PAGE);
    }
}
