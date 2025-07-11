package com.itm.space.controller;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.itm.space.BaseIntegrationTest;
import com.itm.space.constant.RoleConstant;
import com.itm.space.model.request.UpdateOperatorRequest;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class OperatorControllerTest extends BaseIntegrationTest {

    private UUID operatorId;
    private UpdateOperatorRequest updateOperatorRequest;
    private UpdateOperatorRequest InvalidUpdateOperatorRequest;

    @BeforeEach
    public void setup() {
        operatorId = UUID.fromString("9affaac9-fcaf-41f6-a76c-2670234b036e");

        updateOperatorRequest = UpdateOperatorRequest.builder().
                specialization("Техническая поддержка")
                .maxTickets(15)
                .build();

        InvalidUpdateOperatorRequest = UpdateOperatorRequest.builder()
                .specialization("Техническая поддержка")
                .maxTickets(0)
                .build();
    }

    @Test
    @DisplayName("Обновление оператора. Статус 200 - успешно")
    @WithMockUser(authorities = RoleConstant.ADMIN)
    @DataSet(value = "dataset/controller/OperatorController/update/01-operator.yaml",
            cleanBefore = true, cleanAfter = true)
    @ExpectedDataSet(value = "dataset/controller/OperatorController/update/01-expectedOperator.yaml")
    void shouldReturn200WhenUpdated() throws Exception {
        mockMvc.perform(put(OPERATORS_URL + "/{id}", operatorId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateOperatorRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.specialization").value(updateOperatorRequest.getSpecialization()))
                .andExpect(jsonPath("$.maxTickets").value(updateOperatorRequest.getMaxTickets()));

    }

    @Test
    @DisplayName("Обновление оператора. Статус 400 - Неправильные параметры запроса")
    @WithMockUser(authorities = RoleConstant.ADMIN)
    @DataSet(value = "dataset/controller/OperatorController/update/01-operator.yaml",
            cleanBefore = true, cleanAfter = true)
    void shouldReturn400WhenBadRequest() throws Exception {
        mockMvc.perform(put(OPERATORS_URL + "/{id}", operatorId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(InvalidUpdateOperatorRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(BAD_REQUEST_MESSAGE));
    }

    @Test
    @DisplayName("Обновление оператора. Статус 401 - Пользователь не аутентифицирован")
    void shouldReturn401WhenNotAuthorized() throws Exception {
        mockMvc.perform(put(OPERATORS_URL + "/{id}", operatorId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateOperatorRequest)))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message").value(UNAUTHORIZED_MESSAGE));
    }

    @Test
    @DisplayName("Обновление оператора. Статус 403 - Недостаточно прав пользователя")
    @WithMockUser(authorities = RoleConstant.USER)
    void shouldReturn403WhenForbidden() throws Exception {
        mockMvc.perform(put(OPERATORS_URL + "/{id}", operatorId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateOperatorRequest)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value(FORBIDDEN_MESSAGE));
    }

    @Test
    @DisplayName("Обновление оператора. Статус 404 - Пользователь не найден")
    @WithMockUser(authorities = RoleConstant.ADMIN)
    void shouldReturn404WhenNotFound() throws Exception {
        mockMvc.perform(put(OPERATORS_URL + "/{id}", operatorId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateOperatorRequest)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(USER_NOT_FOUND_MESSAGE));
    }
}
