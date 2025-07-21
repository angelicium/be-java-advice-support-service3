package com.itm.space.controller;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.itm.space.BaseIntegrationTest;
import com.itm.space.constant.RoleConstant;
import com.itm.space.model.request.CreateOperatorRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.UUID;

import static com.itm.space.constant.ApiConstant.OPERATORS_URL;
import static com.itm.space.constant.ErrorMessagesConstant.BAD_REQUEST_MESSAGE;
import static com.itm.space.constant.ErrorMessagesConstant.FORBIDDEN_MESSAGE;
import static com.itm.space.constant.ErrorMessagesConstant.UNAUTHORIZED_MESSAGE;
import static com.itm.space.constant.ErrorMessagesConstant.USER_NOT_FOUND_MESSAGE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class OperatorControllerCreateTest extends BaseIntegrationTest {

    private CreateOperatorRequest createOperatorRequest;
    private CreateOperatorRequest createOperator400Request;
    private CreateOperatorRequest createOperator404Request;

    @BeforeEach
    public void setup() {
        UUID userIdNotAssigned = UUID.fromString("7aa45e75-7843-8921-b3fc-3a074a77bbb7");
        UUID userIdNotFound = UUID.randomUUID();

        createOperatorRequest = CreateOperatorRequest.builder()
            .userId(userIdNotAssigned)
            .specialization("Техническая поддержка")
            .maxTickets(10)
            .build();

        createOperator400Request = CreateOperatorRequest.builder()
            .userId(userIdNotAssigned)
            .specialization("Техническая поддержка")
            .maxTickets(0)
            .build();

        createOperator404Request = CreateOperatorRequest.builder()
            .userId(userIdNotFound)
            .specialization("Техническая поддержка")
            .maxTickets(10)
            .build();
    }

    @Test
    @DisplayName("Создание оператора. Статус 201 - успешно")
    @WithMockUser(authorities = RoleConstant.ADMIN)
    @DataSet(value = "dataset/OperatorController.create/01-operator.yaml",
        cleanBefore = true, cleanAfter = true)
    @ExpectedDataSet(value = "dataset/OperatorController.create/01-expectedOperator.yaml",
        ignoreCols = {"id", "created_at"}, orderBy = {"user_id"})
    void createOperator_shouldReturnNewOperatorData() throws Exception {
        mockMvc.perform(post(OPERATORS_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createOperatorRequest)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").isNotEmpty())
            .andExpect(jsonPath("$.name").value("Инокентий"))
            .andExpect(jsonPath("$.specialization").value(createOperatorRequest.getSpecialization()))
            .andExpect(jsonPath("$.maxTickets").value(createOperatorRequest.getMaxTickets()))
            .andExpect(jsonPath("$.createdAt").isNotEmpty());
    }

    @Test
    @DisplayName("Создание оператора. Статус 400 - Неправильные параметры запроса")
    @WithMockUser(authorities = RoleConstant.ADMIN)
    void createOperator_shouldReturnBadRequest() throws Exception {
        mockMvc.perform(post(OPERATORS_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createOperator400Request)))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").value(BAD_REQUEST_MESSAGE));
    }

    @Test
    @DisplayName("Создание оператора. Статус 401 - Пользователь не аутентифицирован")
    void createOperator_shouldReturnUnauthorized() throws Exception {
        mockMvc.perform(post(OPERATORS_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createOperatorRequest)))
            .andExpect(status().isUnauthorized())
            .andExpect(jsonPath("$.message").value(UNAUTHORIZED_MESSAGE));
    }

    @Test
    @DisplayName("Создание оператора. Статус 403 - Недостаточно прав пользователя")
    @WithMockUser(authorities = RoleConstant.USER)
    void createOperator_shouldReturnForbidden() throws Exception {
        mockMvc.perform(post(OPERATORS_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createOperatorRequest)))
            .andExpect(status().isForbidden())
            .andExpect(jsonPath("$.message").value(FORBIDDEN_MESSAGE));
    }

    @Test
    @DisplayName("Создание оператора. Статус 404 - юзера не существует")
    @WithMockUser(authorities = RoleConstant.ADMIN)
    @DataSet(value = "dataset/OperatorController.create/01-operator.yaml",
        cleanBefore = true, cleanAfter = true)
    void createOperator_shouldReturnUserNotFound() throws Exception {
        mockMvc.perform(post(OPERATORS_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createOperator404Request)))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.message").value(USER_NOT_FOUND_MESSAGE));
    }
}
