package ru.jpixel.personaldiaryuserservice.services;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.jpixel.models.dtos.common.Error;
import ru.jpixel.models.dtos.common.OperationResult;
import ru.jpixel.models.dtos.common.Success;
import ru.jpixel.personaldiaryuserservice.domain.open.Diary;
import ru.jpixel.personaldiaryuserservice.domain.secr.User;
import ru.jpixel.personaldiaryuserservice.facades.GoogleGsonFacade;
import ru.jpixel.personaldiaryuserservice.repositories.open.DiaryRepository;

import javax.crypto.KeyGenerator;
import javax.persistence.EntityManager;
import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

@Service
@RequiredArgsConstructor
public class DiaryService {

    private final Logger logger = LoggerFactory.getLogger(DiaryService.class);

    private final DiaryRepository diaryRepository;
    private final EntityManager entityManager;

    @Value("${encryption.key.algorithm}")
    private String algorithmKey;
    @Value("${encryption.key.charset}")
    private String nameCharset;
    @Value("${encryption.algorithm}")
    private String algorithmCipher;

    public OperationResult create(Long userId) {
        var diary = new Diary();
        diary.setUser(entityManager.getReference(User.class, userId));
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
        return new OperationResult(Success.CREATE_DIARY);
    }
}
