package com.itm.space.repository;

import com.itm.space.domain.entity.TicketPriority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketPriorityRepository extends JpaRepository<TicketPriority, Integer> {
}
