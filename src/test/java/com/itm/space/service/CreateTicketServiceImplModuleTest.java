package com.itm.space.service;

import com.itm.space.BaseUnitTest;
import com.itm.space.domain.entity.Ticket;
import com.itm.space.domain.entity.TicketCategory;
import com.itm.space.domain.entity.TicketPriority;
import com.itm.space.domain.entity.TicketStatus;
import com.itm.space.domain.entity.User;
import com.itm.space.model.request.CreateTicketRequest;
import com.itm.space.model.response.CreateTicketResponse;
import com.itm.space.repository.TicketCategoryRepository;
import com.itm.space.repository.TicketPriorityRepository;
import com.itm.space.repository.TicketRepository;
import com.itm.space.repository.TicketStatusRepository;
import com.itm.space.repository.UserRepository;
import com.itm.space.service.impl.CreateTicketServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static com.itm.space.util.SecurityUtil.getCurrentUserId;

public class CreateTicketServiceImplModuleTest extends BaseUnitTest {

    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private TicketCategoryRepository ticketCategoryRepository;

    @Mock
    private TicketPriorityRepository ticketPriorityRepository;

    @Mock
    private TicketStatusRepository ticketStatusRepository;

    @Mock
    private UserRepository userRepository;

    private CreateTicketRequest request;
    private Ticket ticket;
    private TicketPriority priority;
    private TicketCategory category;
    private TicketStatus status;
    private CreateTicketResponse response;
    private User user;

    @InjectMocks
    private CreateTicketServiceImpl createTicketServiceImpl;

    @BeforeEach
    public void setup() {

        request = CreateTicketRequest.builder()
                .title("название")
                .description("тестовое описание")
                .priorityId(1)
                .categoryId(1)
                .build();

        category = TicketCategory.builder()
                .id(1)
                .name("General")
                .description("description")
                .build();

        priority = TicketPriority.builder()
                .id(1)
                .name("High")
                .description("description")
                .build();

        status = TicketStatus.builder()
                .id(1)
                .name("NEW")
                .description("new description")
                .build();

        user = User.builder()
                .id(UUID.randomUUID())
                .name("User")
                .email("email")
                .build();

        ticket = Ticket.builder()
                .id(UUID.randomUUID())
                .title("Test Ticket")
                .description("This is a test ticket.")
                .createdAt(LocalDateTime.now())
                .category(category)
                .priority(priority)
                .status(status)
                .user(user)
                .build();
    }

    @Test
    public void createAndRetrieveTicketTest() {

        when(ticketCategoryRepository.findById(category.getId())).thenReturn(Optional.of(category));
        when(ticketPriorityRepository.findById(priority.getId())).thenReturn(Optional.of(priority));
        when(ticketStatusRepository.findById(status.getId())).thenReturn(Optional.of(status));
        when(userRepository.findById(getCurrentUserId())).thenReturn(Optional.of(user));
        when(ticketRepository.save(any(Ticket.class))).thenReturn(ticket);

        response = createTicketServiceImpl.createAndRetrieveTicket(request);

        assertNotNull(response);
        assertEquals(ticket.getId(), response.getId());
        assertEquals(ticket.getTitle(), response.getTitle());
        assertEquals(ticket.getDescription(), response.getDescription());
        assertEquals(ticket.getStatus().getName(), response.getStatus());
        assertEquals(ticket.getCreatedAt(), response.getCreatedAt());
        assertEquals(ticket.getPriority().getName(), response.getPriority());
        assertEquals(ticket.getCategory().getId(), response.getCategoryId());

        verify(ticketCategoryRepository).findById(1);
        verify(ticketPriorityRepository).findById(1);
        verify(ticketStatusRepository).findById(1);
        verify(userRepository).findById(getCurrentUserId());
        verify(ticketRepository).save(any(Ticket.class));
    }
}
