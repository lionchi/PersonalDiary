package ru.jpixel.personaldiaryservice.services;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.jpixel.models.dtos.common.Error;
import ru.jpixel.models.dtos.common.OperationResult;
import ru.jpixel.models.dtos.common.Success;
import ru.jpixel.personaldiaryservice.domain.open.Diary;
import ru.jpixel.personaldiaryservice.facades.GoogleGsonFacade;
import ru.jpixel.personaldiaryservice.repositories.open.DiaryRepository;

import javax.crypto.KeyGenerator;
import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

@Service
@RequiredArgsConstructor
public class DiaryService {

    private final Logger logger = LoggerFactory.getLogger(DiaryService.class);

    private final DiaryRepository diaryRepository;

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
}
