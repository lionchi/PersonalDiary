package ru.jpixel.personaldiaryservice.facades;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.jpixel.personaldiaryservice.exceptions.CryptoFacadeException;

import static org.junit.jupiter.api.Assertions.*;

public class CryptoFacadeTest {

    @Test
    @DisplayName("Проверка фасада для шифрования")
    public void test() throws CryptoFacadeException {
        var cryptoFacade = new CryptoFacade("AES", "GBK", "AES/ECB/PKCS5Padding");

        var key = cryptoFacade.keyGeneration();

        assertNotNull(cryptoFacade.keyGeneration());

        var content = "ТекстТекстТекстТекс";

        var encryptContent = cryptoFacade.encryptContent(key, content.getBytes());

        assertNotEquals(content, encryptContent);

        var decryptContent = cryptoFacade.decryptContent(key, encryptContent);

        assertEquals(content, decryptContent);
    }
}
