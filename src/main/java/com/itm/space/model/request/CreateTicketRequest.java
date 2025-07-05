package com.itm.space.model.request;

import com.itm.space.model.TicketPriority;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateTicketRequest {

    @NotBlank(message = "Название не может быть пустым")
    @Size(max = 255)
    private String title;

    @NotBlank(message = "Описание не может быть пустым")
    @Size(max = 2000)
    private String description;

    private TicketPriority priority;

    private Integer categoryId;
}