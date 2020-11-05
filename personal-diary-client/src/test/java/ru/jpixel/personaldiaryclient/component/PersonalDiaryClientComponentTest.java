package ru.jpixel.personaldiaryclient.component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import ru.jpixel.models.dtos.common.Error;
import ru.jpixel.models.dtos.common.OperationResult;
import ru.jpixel.personaldiaryclient.web.security.JwtInfo;
import ru.jpixel.personaldiaryclient.web.security.PersonalDiaryUser;

import java.text.MessageFormat;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PersonalDiaryClientComponentTest {

    @Autowired
    private JwtInfo jwtInfo;
    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    @DisplayName("Проверка доступа для авторизованного пользователя")
    public void firstCaseTest() {
        var now = System.currentTimeMillis();
        var token = Jwts.builder()
                .setSubject("test")
                .claim(jwtInfo.getClaimAuthorities(), List.of("ROLE_USER","ROLE_ADMIN"))
                .claim(jwtInfo.getClaimUserId(), "1")
                .claim(jwtInfo.getClaimDiaryId(), "1")
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + jwtInfo.getExpiration() * 1000))
                .signWith(SignatureAlgorithm.HS512, jwtInfo.getSecret().getBytes())
                .compact();

        var headers = new HttpHeaders();
        headers.set("Cookie", MessageFormat.format("{0}={1}", jwtInfo.getAccessCookieName(), token));

        var response = testRestTemplate.exchange("/api/account/information",
                HttpMethod.GET, new HttpEntity<>(headers), PersonalDiaryUser.class);

        assertEquals(200, response.getStatusCodeValue());

        var personalDiaryUser = response.getBody();
        assertNotNull(personalDiaryUser);
        assertEquals(1L, personalDiaryUser.getId());
    }

    @Test
    @DisplayName("Проверка доступа для неавторизованного пользователя")
    public void secondCaseTest() {
        var response = testRestTemplate.getForEntity("/api/account/information", OperationResult.class);

        assertEquals(403, response.getStatusCodeValue());

        var operationResult = response.getBody();
        assertNotNull(operationResult);
        assertEquals(Error.ACCESS_IS_DENIED.getCode(), operationResult.getCode());
    }
}
