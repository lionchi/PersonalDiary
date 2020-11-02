package ru.jpixel.personaldiaryservice.facades;

import lombok.RequiredArgsConstructor;
import ru.jpixel.personaldiaryservice.exceptions.CryptoFacadeException;

import javax.crypto.*;
import javax.xml.bind.DatatypeConverter;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

@RequiredArgsConstructor
public class CryptoFacade {

    private final String algorithmKey;
    private final String nameCharset;
    private final String algorithmCipher;

    public byte[] keyGeneration() throws CryptoFacadeException {
        try {
            var keyGenerator = KeyGenerator.getInstance(algorithmKey);
            keyGenerator.init(256);
            var key = keyGenerator.generateKey();
            var keyJson = new GoogleGsonFacade<>(Key.class).toJson(key);
            return keyJson.getBytes(nameCharset);
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            throw new CryptoFacadeException("Ошибка генерации секретного ключа", e);
        }
    }

    public String encryptContent(byte[] key, byte[] content) throws CryptoFacadeException {
        try {
            var cipher = Cipher.getInstance(algorithmCipher);
            var keyOfJson = new GoogleGsonFacade<>(Key.class).fromJson(key, nameCharset);
            cipher.init(Cipher.ENCRYPT_MODE, keyOfJson);
            var encrypted = cipher.doFinal(content);
            return DatatypeConverter.printHexBinary(encrypted);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | UnsupportedEncodingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            throw new CryptoFacadeException("Ошибка при шифровании данных", e);
        }
    }

    public String decryptContent(byte[] key, String encryptedData) throws CryptoFacadeException {
        try {
            var cipher = Cipher.getInstance(algorithmCipher);
            var keyOfJson = new GoogleGsonFacade<>(Key.class).fromJson(key, nameCharset);
            cipher.init(Cipher.DECRYPT_MODE, keyOfJson);
            return new String(cipher.doFinal(DatatypeConverter.parseHexBinary(encryptedData)));
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | UnsupportedEncodingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            throw new CryptoFacadeException("Ошибка при дешифровании данных", e);
        }
    }
}
