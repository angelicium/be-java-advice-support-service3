package com.itm.space.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table (name = "ticket_priority")
@Builder
public class TicketPriority {
    @Id
    private Integer id;
    @Column (nullable = false)
    private String name;
    @Column
    private String description;

}
