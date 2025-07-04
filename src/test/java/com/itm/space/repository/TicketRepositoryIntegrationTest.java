package com.itm.space.repository;

import com.itm.space.BaseIntegrationTest;
import com.itm.space.domain.entity.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.UUID;

public class TicketRepositoryIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TicketCategoryRepository ticketCategoryRepository;
    @Autowired
    private TicketStatusRepository ticketStatusRepository;
    @Autowired
    private TicketPriorityRepository ticketPriorityRepository;

    private User testUser;
    private TicketCategory testCategory;
    private TicketStatus testStatus;
    private TicketPriority testPriority;

    @BeforeEach
    public void setUp() {
        testCategory = TicketCategory.builder()
                .id(1)
                .name("testCategory")
                .description("testCategoryDescription")
                .build();
        ticketCategoryRepository.save(testCategory);

        testUser = User.builder()
                .name("testUser")
                .email("testUser@test.com")
                .build();
        userRepository.save(testUser);

        testStatus = TicketStatus.builder()
                .id(1)
                .name("testTicketStatus")
                .description("testTicketStatusDescription")
                .build();
        ticketStatusRepository.save(testStatus);

        testPriority = TicketPriority.builder()
                .id(1)
                .name("testTicketPriority")
                .description("testTicketPriorityDescription")
                .build();
        ticketPriorityRepository.save(testPriority);
    }

    @Test
    @DisplayName("Тест на добавление сущности Ticket в БД")
    public void shouldSaveAndRetrieveTicket() {
        Ticket ticket = ticketInit();

        ticketRepository.save(ticket);

        Ticket retrievedTicket = ticketRepository.findById(ticket.getId()).orElseThrow();
        assertNotNull(retrievedTicket);
        assertEquals(ticket.getId(), retrievedTicket.getId());
        assertEquals(ticket.getTitle(), retrievedTicket.getTitle());
        assertEquals(ticket.getDescription(), retrievedTicket.getDescription());
    }

    @Test
    @DisplayName("Тест на обновление сущности Ticket в БД")
    public void shouldUpdateAndRetrieveTicket() {

        Ticket ticket = ticketInit();

        ticketRepository.save(ticket);

        ticket.setTitle("Updated Ticket Title");
        ticket.setDescription("Updated Ticket Description");
        ticketRepository.save(ticket);

        Ticket updatedTicket = ticketRepository.findById(ticket.getId()).orElseThrow();
        assertEquals("Updated Ticket Title", updatedTicket.getTitle());
        assertEquals("Updated Ticket Description", updatedTicket.getDescription());
    }

    private Ticket ticketInit(){

        return Ticket.builder()
                .id(UUID.randomUUID())
                .user(userRepository.findAll().getFirst())
                .category(testCategory)
                .priority(testPriority)
                .status(testStatus)
                .title("Ticket Title")
                .description("Ticket Description")
                .slaDeadline(LocalDateTime.now())
                .escalatedAt(LocalDateTime.now())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .closedAt(null)
                .build();
    }
}
