package com.itm.space.model.request;

import com.itm.space.domain.entity.TicketPriority;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateTicketRequest {

    @NotBlank (message = "Название не может быть пустым")
    @Size(max = 255)
    private String title;

    @NotBlank (message = "Описание не может быть пустым")
    @Size(max = 2000)
    private String description;

    @NotNull (message = "Айди не может быть пустым")
    private Integer priorityId;

    @NotNull (message = "Категория не может быть пустой")
    private Integer categoryId;
}