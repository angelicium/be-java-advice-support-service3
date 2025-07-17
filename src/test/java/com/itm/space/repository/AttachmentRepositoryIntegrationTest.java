package com.itm.space.repository;

import com.itm.space.BaseIntegrationTest;
import com.itm.space.domain.entity.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AttachmentRepositoryIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private AttachmentRepository attachmentRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private TicketCategoryRepository ticketCategoryRepository;
    @Autowired
    private TicketStatusRepository ticketStatusRepository;
    @Autowired
    private TicketPriorityRepository ticketPriorityRepository;

    private Attachment testAttachment;

    @BeforeEach
    public void setUp() {
        TicketCategory testCategory = TicketCategory.builder()
            .id(1)
            .name("testCategory")
            .description("testCategoryDescription")
            .build();
        ticketCategoryRepository.save(testCategory);

        User testUser = User.builder()
            .name("testUser")
            .email("testUser@example.com")
            .build();
        userRepository.save(testUser);

        TicketStatus testStatus = TicketStatus.builder()
            .id(1)
            .name("testTicketStatus")
            .description("testTicketStatusDescription")
            .build();
        ticketStatusRepository.save(testStatus);

        TicketPriority testPriority = TicketPriority.builder()
            .id(1)
            .name("testTicketPriority")
            .description("testTicketPriorityDescription")
            .build();
        ticketPriorityRepository.save(testPriority);

        Ticket testTicket = Ticket.builder()
            .id(UUID.randomUUID())
            .user(testUser)
            .category(testCategory)
            .priority(testPriority)
            .status(testStatus)
            .title("Test Ticket")
            .description("Test Ticket Description")
            .slaDeadline(LocalDateTime.now())
            .escalatedAt(LocalDateTime.now())
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .closedAt(null)
            .build();
        ticketRepository.save(testTicket);

        testAttachment = Attachment.builder()
            .ticket(testTicket)
            .file(UUID.randomUUID())
            .build();
    }

    @Test
    @DisplayName("Тест на добавление сущности Attachment в БД")
    public void shouldSaveAndRetrieveAttachment() {
        attachmentRepository.save(testAttachment);

        Attachment retrievedAttachment = attachmentRepository.findById(testAttachment.getId())
            .orElseThrow(() -> new AssertionError("Attachment not found"));

        assertEquals(retrievedAttachment.getId(), testAttachment.getId());
        assertEquals(retrievedAttachment.getTicket().getId(), testAttachment.getTicket().getId());
        assertEquals(retrievedAttachment.getFile(), testAttachment.getFile());
    }

    @Test
    @DisplayName("Тест на обновление сущности Attachment в БД")
    public void shouldUpdateAndRetrieveAttachment() {
        attachmentRepository.save(testAttachment);

        UUID newFileId = UUID.randomUUID();
        testAttachment.setFile(newFileId);

        attachmentRepository.save(testAttachment);

        Attachment updatedAttachment = attachmentRepository.findById(testAttachment.getId())
            .orElseThrow(() -> new AssertionError("Attachment not found"));

        assertEquals(updatedAttachment.getFile(), newFileId);
    }

}
