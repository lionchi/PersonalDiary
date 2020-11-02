package ru.jpixel.personaldiarymailservice.controllers.mail;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.jpixel.models.dtos.common.OperationResult;
import ru.jpixel.models.dtos.common.Success;
import ru.jpixel.models.dtos.secr.PasswordResetTokenDto;
import ru.jpixel.personaldiarymailservice.controllers.BaseControllerTest;
import ru.jpixel.personaldiarymailservice.services.MailService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

public class MailControllerTest extends BaseControllerTest {

    @MockBean
    private MailService mailService;

    @Test
    @DisplayName("mail api method sendRecoveryPasswordMail")
    public void sendRecoveryPasswordMailTest() throws Exception {
        var operationResult = new OperationResult(Success.RECOVERY_PASSWORD_SEND_MESSAGE);

        Mockito.when(mailService.sendRecoveryPasswordMail(any(PasswordResetTokenDto.class), anyString()))
                .thenReturn(operationResult);

        mockMvc.perform(MockMvcRequestBuilders.post("/sendRecoveryPasswordMail")
                .queryParam("ln", "ru")
                .content(asJsonString(new PasswordResetTokenDto()))
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(operationResult.getCode()));
    }
}
