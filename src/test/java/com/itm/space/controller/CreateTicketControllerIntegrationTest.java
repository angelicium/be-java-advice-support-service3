package com.itm.space.controller;

import com.itm.space.BaseIntegrationTest;
import com.itm.space.model.request.CreateTicketRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;

import static com.itm.space.constant.RoleConstant.USER;
import static com.itm.space.constant.ErrorMessagesConstant.BAD_REQUEST_MESSAGE;
import static com.itm.space.constant.ErrorMessagesConstant.FORBIDDEN_MESSAGE;
import static com.itm.space.constant.ErrorMessagesConstant.UNAUTHORIZED_MESSAGE;
import static com.itm.space.constant.ErrorMessagesConstant.INTERNAL_SERVER_ERROR;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class CreateTicketControllerIntegrationTest extends BaseIntegrationTest {

    private CreateTicketRequest validRequest;
    private CreateTicketRequest invalidRequest;

    @BeforeEach
    void setUp() {
        validRequest = CreateTicketRequest.builder()
                .title("название")
                .description("тестовое описание")
                .priorityId(1)
                .categoryId(1)
                .build();

        invalidRequest = CreateTicketRequest.builder()
                .title("")
                .description("тестовое описание")
                .priorityId(1)
                .categoryId(1)
                .build();
    }

    @Test
    @WithMockUser(authorities = USER)
    @DisplayName("Создание тикета. Статус 200: Успешно")
    void createAndRetrieveTicket() throws Exception {

        mockMvc.perform(post("/api/v1/tickets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(validRequest.getTitle()))
                .andExpect(jsonPath("$.description").value(validRequest.getDescription()))
                .andExpect(jsonPath("$.priorityId").value(validRequest.getPriorityId()))
                .andExpect(jsonPath("$.categoryId").value(validRequest.getCategoryId()));
    }

    @Test
    @WithMockUser(authorities = USER)
    @DisplayName("Создание тикета. Статус 400: Неправильные параметры запроса")
    void shouldReturn400WhenBadRequest() throws Exception {
        mockMvc.perform(post("/api/v1/tickets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(BAD_REQUEST_MESSAGE));
    }

    @Test
    @DisplayName("Создание тикета. Статус 401: Пользователь не аутентифицирован")
    void shouldReturn401WhenNotAuthorized() throws Exception {

        mockMvc.perform(post("/api/v1/tickets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRequest)))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message").value(UNAUTHORIZED_MESSAGE));
    }

    @Test
    @WithMockUser(authorities = "OTHER_ROLE")
    @DisplayName("Создание тикета. Статус 403: Недостаточно прав пользователя")
    void shouldReturn403WhenForbidden() throws Exception {

        mockMvc.perform(post("/api/v1/tickets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRequest)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value(FORBIDDEN_MESSAGE));
    }

    @Test
    @WithMockUser(authorities = USER)
    @DisplayName("Создание тикета. Статус 500: Внутренняя ошибка сервера")
    void shouldReturn500WhenInternalServerError() throws Exception {

        mockMvc.perform(post("/api/v1/tickets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validRequest)))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value(INTERNAL_SERVER_ERROR));
    }
}