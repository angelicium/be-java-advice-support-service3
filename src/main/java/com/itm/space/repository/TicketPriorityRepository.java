package com.itm.space.repository;

import com.itm.space.domain.entity.TicketPriority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketPriorityRepository extends JpaRepository<TicketPriority, Integer> {
}
