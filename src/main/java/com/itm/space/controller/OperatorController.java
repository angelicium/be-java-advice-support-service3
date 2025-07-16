package com.itm.space.controller;

import com.itm.space.model.request.CreateOperatorRequest;
import com.itm.space.model.request.UpdateOperatorRequest;
import com.itm.space.model.response.OperatorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

import static com.itm.space.constant.ApiConstant.OPERATORS_URL;
import static com.itm.space.constant.RoleConstant.ADMIN;

@RequestMapping(OPERATORS_URL)
@Tag(name = "Operator Controller", description = "Управление операторами")
public interface OperatorController {

    @PostMapping
    @Secured(ADMIN)
    @Operation(summary = "Operator creating method",
        description = "Создание оператора",
        security = @SecurityRequirement(name = "keycloak_oauth_scheme")
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Оператор успешно создан"),
        @ApiResponse(responseCode = "400", description = "Неправильные параметры запроса"),
        @ApiResponse(responseCode = "401", description = "Пользователь не аутентифицирован"),
        @ApiResponse(responseCode = "403", description = "Недостаточно прав пользователя"),
        @ApiResponse(responseCode = "404", description = "Пользователь не найден"),
        @ApiResponse(responseCode = "500", description = "Внутренние ошибки сервиса")
    })
    ResponseEntity<OperatorResponse> createOperator(@RequestBody @Valid CreateOperatorRequest createOperatorRequest);

    @PutMapping("/{id}")
    @Secured(ADMIN)
    @Operation(summary = "Operator update method",
        description = "Метод обновления оператора",
        security = @SecurityRequirement(name = "keycloak_oauth_scheme"))
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode = "400", description = "Неправильные параметры запроса"),
        @ApiResponse(responseCode = "401", description = "Пользователь не аутентифицирован"),
        @ApiResponse(responseCode = "403", description = "Недостаточно прав пользователя"),
        @ApiResponse(responseCode = "404", description = "Пользователь не найден"),
        @ApiResponse(responseCode = "500", description = "Внутренние ошибки сервиса")
    })
    ResponseEntity<OperatorResponse> updateOperator(@PathVariable UUID id,
                                                    @RequestBody @Valid UpdateOperatorRequest updateOperatorRequest);
}
