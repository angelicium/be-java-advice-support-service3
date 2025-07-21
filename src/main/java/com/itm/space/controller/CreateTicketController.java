package com.itm.space.controller;

import com.itm.space.model.request.CreateTicketRequest;
import com.itm.space.model.response.CreateTicketResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.itm.space.constant.ApiConstant.CREATE_TICKET_URL;
import static com.itm.space.constant.RoleConstant.USER;

@RequestMapping(CREATE_TICKET_URL)
@Tag(name = "Create Ticket Controller", description = "Создание тикета")
public interface CreateTicketController {

    @PostMapping()
    @Secured(USER)
    @Operation(security = @SecurityRequirement(name = "keycloak_oauth_scheme"))
    CreateTicketResponse createTicket(@RequestBody @Valid CreateTicketRequest request);
}