package com.itm.space.controller.impl;

import com.itm.space.controller.CreateTicketController;
import com.itm.space.model.request.CreateTicketRequest;
import com.itm.space.model.response.CreateTicketResponse;
import com.itm.space.service.CreateTicketService;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Schema(description = "Создает и возвращает тикет")
public class CreateTicketControllerImpl implements CreateTicketController {

    private final CreateTicketService createTicketService;

    @Override
    public CreateTicketResponse createTicket(CreateTicketRequest request) {

        return createTicketService.createAndRetrieveTicket(request);
    }
}
