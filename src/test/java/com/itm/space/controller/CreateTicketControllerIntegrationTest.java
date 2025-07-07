package com.itm.space.controller;

import com.itm.space.BaseIntegrationTest;
import com.itm.space.model.request.CreateTicketRequest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;

import static com.itm.space.constant.RoleConstant.USER;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CreateTicketControllerIntegrationTest extends BaseIntegrationTest {

    @Test
    @WithMockUser(authorities = USER)
    void createAndRetrieveTicket()  throws Exception {
        CreateTicketRequest request = new CreateTicketRequest();
        request.setTitle("тестовое название");
        request.setDescription("тестовое описание");
        request.setPriorityId(1);
        request.setCategoryId(1);

        mockMvc.perform(post("/api/v1/tickets") // отправили запрос со всеми тестовыми параметрами
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk()); // провалидировали, надо будет еще провалидировать то что
    }
}
