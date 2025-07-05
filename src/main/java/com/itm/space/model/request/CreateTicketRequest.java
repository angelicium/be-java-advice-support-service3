package com.itm.space.model.request;

import com.itm.space.model.TicketPriority;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateTicketRequest {

    private String title;

    private String description;

    private TicketPriority priority;

    private Integer categoryId;
}