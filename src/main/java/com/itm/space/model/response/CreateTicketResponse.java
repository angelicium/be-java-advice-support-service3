package com.itm.space.model.response;

import com.itm.space.model.TicketPriority;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateTicketResponse {

    private String title;

    private String description;

    private TicketPriority priority;

    private String status;

    private LocalDateTime createdAt;

    private Integer categoryId;

    private UUID assignedOperatorId;
}
