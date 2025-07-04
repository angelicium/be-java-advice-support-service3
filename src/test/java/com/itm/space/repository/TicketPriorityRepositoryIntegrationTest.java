package com.itm.space.repository;

import com.itm.space.BaseIntegrationTest;
import com.itm.space.domain.entity.TicketPriority;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class TicketPriorityRepositoryIntegrationTest extends BaseIntegrationTest {
    @Autowired
    private TicketPriorityRepository ticketPriorityRepository;

    @Test
    @DisplayName("Тест на добавление сущности TicketPriority в БД")
    public void shouldCreateAndRetrieveTicketPriority() {
        TicketPriority ticketPriority = new TicketPriority();
        ticketPriority.setId(1);
        ticketPriority.setName("Test Ticket Priority");
        ticketPriority.setDescription("Test Ticket Priority Description");

        ticketPriorityRepository.save(ticketPriority);
        Optional<TicketPriority> FoundStatus = ticketPriorityRepository.findById(ticketPriority.getId());

        Assertions.assertTrue(FoundStatus.isPresent());
        Assertions.assertEquals(ticketPriority.getName(), FoundStatus.get().getName());
        Assertions.assertEquals(ticketPriority.getDescription(), FoundStatus.get().getDescription());
    }

    @Test
    @DisplayName("Тест на обновление сущности TicketPriority в БД")
    public void shouldUpdateTicketPriority() {
        TicketPriority ticketPriority = new TicketPriority();
        ticketPriority.setId(1);
        ticketPriority.setName("Новый TicketPriority");
        ticketPriority.setDescription("Новое TicketPriority Description");
        TicketPriority updatedTicketPriority = ticketPriorityRepository.save(ticketPriority);

        updatedTicketPriority.setName("Обновленный TicketPriority");
        updatedTicketPriority.setDescription("Обновленное TicketPriority Description");
        ticketPriorityRepository.save(updatedTicketPriority);

        Optional<TicketPriority> updatedPriority = ticketPriorityRepository.findById(updatedTicketPriority.getId());

        Assertions.assertTrue(updatedPriority.isPresent());
        Assertions.assertEquals("Обновленный TicketPriority", updatedPriority.get().getName());
        Assertions.assertEquals("Обновленное TicketPriority Description", updatedPriority.get().getDescription());
    }
}
