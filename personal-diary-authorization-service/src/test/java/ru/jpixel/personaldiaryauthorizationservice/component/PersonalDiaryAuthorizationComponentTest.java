package ru.jpixel.personaldiaryauthorizationservice.component;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.jpixel.models.dtos.common.Error;
import ru.jpixel.models.dtos.common.OperationResult;
import ru.jpixel.models.dtos.secr.UserCredentials;
import ru.jpixel.models.dtos.secr.UserDto;
import ru.jpixel.personaldiaryauthorizationservice.security.JwtInfo;
import ru.jpixel.personaldiaryauthorizationservice.services.DiaryServiceFeignClient;
import ru.jpixel.personaldiaryauthorizationservice.services.UserServiceFeignClient;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PersonalDiaryAuthorizationComponentTest {

    @Autowired
    private TestRestTemplate testRestTemplate;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private JwtInfo jwtInfo;
    @MockBean
    private UserServiceFeignClient userServiceFeignClient;
    @MockBean
    private DiaryServiceFeignClient diaryServiceFeignClient;

    @BeforeEach
    public void init() {
        var username = "test";
        var diaryId = 1L;

        var userDto = new UserDto();
        userDto.setId(1L);
        userDto.setLogin(username);
        userDto.setPassword(passwordEncoder.encode("12131415"));
        userDto.setRoles(List.of("USER", "ADMIN"));

        Mockito.when(userServiceFeignClient.findByLogin(anyString()))
                .thenReturn(userDto);

        Mockito.when(diaryServiceFeignClient.findDiaryIdByUserId(anyLong()))
                .thenReturn(diaryId);
    }

    @Test
    @DisplayName("Успешная авторизация")
    public void componentFirstCaseTest() {
        var userCredentials = new UserCredentials();
        userCredentials.setUsername("test");
        userCredentials.setPassword("12131415");

        HttpEntity<UserCredentials> request = new HttpEntity<>(userCredentials);

        var response = testRestTemplate.postForEntity("/auth", request, String.class);
        assertEquals(200, response.getStatusCodeValue());

        var strings = response.getHeaders().get("Set-Cookie");
        assertNotNull(strings);
        assertTrue(strings.stream().anyMatch(s -> s.contains(jwtInfo.getAccessCookieName())));
    }

    @Test
    @DisplayName("Неправильно введены учетные данные")
    public void componentSecondCaseTest() {
        var userCredentials = new UserCredentials();
        userCredentials.setUsername("test");
        userCredentials.setPassword("111111111");

        HttpEntity<UserCredentials> request = new HttpEntity<>(userCredentials);

        var response = testRestTemplate.postForEntity("/auth", request, OperationResult.class);
        assertEquals(401, response.getStatusCodeValue());

        var operationResult = response.getBody();
        assertNotNull(operationResult);
        assertEquals(Error.AUTHORIZATION.getCode(), operationResult.getCode());
    }
}
