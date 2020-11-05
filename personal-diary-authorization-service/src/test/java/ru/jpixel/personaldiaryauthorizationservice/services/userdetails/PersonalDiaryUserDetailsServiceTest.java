package ru.jpixel.personaldiaryauthorizationservice.services.userdetails;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.jpixel.models.dtos.secr.UserDto;
import ru.jpixel.personaldiaryauthorizationservice.services.BaseServiceTest;
import ru.jpixel.personaldiaryauthorizationservice.services.DiaryServiceFeignClient;
import ru.jpixel.personaldiaryauthorizationservice.services.PersonalDiaryUserDetailsService;
import ru.jpixel.personaldiaryauthorizationservice.services.UserServiceFeignClient;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;

public class PersonalDiaryUserDetailsServiceTest extends BaseServiceTest {

    @MockBean
    private UserServiceFeignClient userServiceFeignClient;
    @MockBean
    private DiaryServiceFeignClient diaryServiceFeignClient;
    @Autowired
    private PersonalDiaryUserDetailsService personalDiaryUserDetailsService;

    @Test
    @DisplayName("Загрузка пользователя first case")
    public void loadUserByUsernameFirstCaseTest() {
        var username = "test";

        Mockito.when(userServiceFeignClient.findByLogin(anyString()))
                .thenReturn(null);

        assertThrows(UsernameNotFoundException.class, () -> personalDiaryUserDetailsService.loadUserByUsername(username));
    }

    @Test
    @DisplayName("Загрузка пользователя second case")
    public void loadUserByUsernameSecondCaseTest() {
        var username = "test";
        var diaryId = 1L;

        var userDto = new UserDto();
        userDto.setId(1L);
        userDto.setLogin(username);
        userDto.setPassword(UUID.randomUUID().toString());
        userDto.setRoles(List.of("USER", "ADMIN"));

        Mockito.when(userServiceFeignClient.findByLogin(anyString()))
                .thenReturn(userDto);

        Mockito.when(diaryServiceFeignClient.findDiaryIdByUserId(anyLong()))
                .thenReturn(diaryId);

        var personalDiaryUser = assertDoesNotThrow(() -> personalDiaryUserDetailsService.loadUserByUsername(username));

        assertEquals(userDto.getLogin(), personalDiaryUser.getUsername());
    }
}
