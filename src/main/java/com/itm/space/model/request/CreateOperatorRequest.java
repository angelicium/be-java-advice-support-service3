package com.itm.space.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class CreateOperatorRequest {
    @NotNull(message = "ID пользователя не может быть пустым")
    private UUID userId;

    @Size(max = 50)
    @Schema(example = "Техническая поддержка", maxLength = 50)
    private String specialization;

    @NotNull(message = "Поле тикетов не может быть пустым")
    @Min(value = 1, message = "Количество тикетов не может быть меньше 1")
    @Schema(example = "15")
    private Integer maxTickets;
}
