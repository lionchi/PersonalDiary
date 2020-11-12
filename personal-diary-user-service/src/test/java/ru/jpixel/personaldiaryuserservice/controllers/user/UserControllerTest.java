package ru.jpixel.personaldiaryuserservice.controllers.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.jpixel.models.dtos.common.OperationResult;
import ru.jpixel.models.dtos.common.Success;
import ru.jpixel.models.dtos.secr.PasswordResetTokenDto;
import ru.jpixel.models.dtos.secr.RecoveryPasswordDto;
import ru.jpixel.models.dtos.secr.UserDto;
import ru.jpixel.personaldiaryuserservice.controllers.BaseControllerTest;
import ru.jpixel.personaldiaryuserservice.services.UserService;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

public class UserControllerTest extends BaseControllerTest {

    @MockBean
    private UserService userService;

    @Test
    @DisplayName("user api method save")
    public void saveTest() throws Exception {
        var userDto = new UserDto();
        userDto.setName("Иван");
        userDto.setPassword("11111");
        userDto.setLogin("test");
        userDto.setEmail("test@mail.ru");
        userDto.setPrefix("+7");
        userDto.setPhone("9109109191");

        var operationResult = new OperationResult(Success.REGISTRATION);

        Mockito.when(userService.save(any()))
                .thenReturn(operationResult);

        var actions = mockMvc.perform(MockMvcRequestBuilders.post("/save")
                .content(asJsonString(userDto))
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        var response = asObject(actions.andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8), OperationResult.class);

        assertEquals(operationResult.getCode(), response.getCode());
    }

    @Test
    @DisplayName("user api method update")
    public void updateTest() throws Exception {
        var userDto = new UserDto();
        userDto.setName("Иван");
        userDto.setLogin("test");
        userDto.setEmail("test@mail.ru");
        userDto.setPrefix("+7");
        userDto.setPhone("9109109191");

        var operationResult = new OperationResult(Success.BASE_OPERATION);

        Mockito.when(userService.update(any()))
                .thenReturn(operationResult);

        mockMvc.perform(MockMvcRequestBuilders.put("/update")
                .content(asJsonString(userDto))
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(operationResult.getCode()));
    }

    @Test
    @DisplayName("user api method delete")
    public void deleteTest() throws Exception {
        var userId = 1L;

        var operationResult = new OperationResult(Success.DELETE_USER);

        Mockito.when(userService.delete(userId)).thenReturn(operationResult);

        mockMvc.perform(MockMvcRequestBuilders.delete("/delete")
                .queryParam("userId", String.valueOf(userId))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(operationResult.getCode()));
    }

    @Test
    @DisplayName("user api method findByLogin")
    public void findByLoginTest() throws Exception {
        var userDto = new UserDto();
        userDto.setId(999L);

        Mockito.when(userService.findByLogin(anyString()))
                .thenReturn(userDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/findByLogin/{login}", "test")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(userDto.getId()));
    }

    @Test
    @DisplayName("user api method createPasswordResetToken")
    public void createPasswordResetTokenTest() throws Exception {
        var passwordResetTokenRequest = new PasswordResetTokenDto();
        passwordResetTokenRequest.setUserEmail("test@mail.ru");

        var operationResult = new OperationResult(Success.PASSWORD_RESET_TOKEN_CREATE);

        Mockito.when(userService.createPasswordResetToken(any()))
                .thenReturn(operationResult);

        ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.post("/create/passwordResetToken")
                .content(asJsonString(passwordResetTokenRequest))
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        var response = asObject(actions.andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8), OperationResult.class);

        assertEquals(operationResult.getCode(), response.getCode());
    }

    @Test
    @DisplayName("user api method recoveryPassword")
    public void recoveryPasswordTest() throws Exception {
        var passwordDto = new RecoveryPasswordDto();
        passwordDto.setNewPassword("22222");
        passwordDto.setToken(UUID.randomUUID().toString());

        var operationResult = new OperationResult(Success.RECOVERY_PASSWORD);

        Mockito.when(userService.recoveryPassword(any()))
                .thenReturn(operationResult);

        ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.put("/recoveryPassword")
                .content(asJsonString(passwordDto))
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        var response = asObject(actions.andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8), OperationResult.class);

        assertEquals(operationResult.getCode(), response.getCode());
    }

    @Test
    @DisplayName("user api method findTokenByEmail")
    public void findTokenByEmailTest() throws Exception {
        var operationResult = new OperationResult(Success.BASE_OPERATION);

        Mockito.when(userService.findTokenByEmail(anyString()))
                .thenReturn(operationResult);

        ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.get("/findTokenByEmail/{email}", "test@mail.ru")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        var response = asObject(actions.andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8), OperationResult.class);

        assertEquals(operationResult.getCode(), response.getCode());
    }

    @Test
    @DisplayName("user api method searchForUserToNotify")
    public void searchForUserToNotifyTest() throws Exception {
        Mockito.when(userService.searchForUserToNotify())
                .thenReturn(Collections.emptyList());

        mockMvc.perform(MockMvcRequestBuilders.get("/searchForUserToNotify")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}