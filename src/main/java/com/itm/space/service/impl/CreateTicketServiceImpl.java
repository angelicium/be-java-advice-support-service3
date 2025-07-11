package com.itm.space.service.impl;

import com.itm.space.domain.entity.Ticket;
import com.itm.space.domain.entity.TicketStatus;
import com.itm.space.domain.entity.User;
import com.itm.space.model.request.CreateTicketRequest;
import com.itm.space.model.response.CreateTicketResponse;
import com.itm.space.repository.TicketCategoryRepository;
import com.itm.space.repository.TicketPriorityRepository;
import com.itm.space.repository.TicketRepository;
import com.itm.space.service.CreateTicketService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.itm.space.constant.ErrorMessagesConstant.CATEGORY_NOT_FOUND_MESSAGE;
import static com.itm.space.constant.ErrorMessagesConstant.PRIORITY_NOT_FOUND_EXCEPTION;

@Service
@AllArgsConstructor
public class CreateTicketServiceImpl implements CreateTicketService {

    private final TicketCategoryRepository ticketCategoryRepository;
    private final TicketPriorityRepository ticketPriorityRepository;
    private final TicketRepository ticketRepository;

    @Override
    public CreateTicketResponse createAndRetrieveTicket(CreateTicketRequest request) {

        CreateTicketResponse ticketResponse = new CreateTicketResponse();
        Ticket ticket = ticketInit(request);
        ticketResponse.setId(ticket.getId());
        ticketResponse.setTitle(ticket.getTitle());
        ticketResponse.setDescription(ticket.getDescription());
        ticketResponse.setCreatedAt(ticket.getCreatedAt());
        ticketResponse.setStatus(ticket.getStatus().getName());
        ticketResponse.setPriority(ticket.getPriority().getName());
        ticketResponse.setCategoryId(ticket.getCategory().getId());

        return ticketResponse;
    }

    private Ticket ticketInit(CreateTicketRequest request) {

        Ticket ticket = new Ticket();
        ticket.setUser(new User());
        ticket.setCategory(ticketCategoryRepository.findById(request.getCategoryId()).orElseThrow(() -> new EntityNotFoundException(CATEGORY_NOT_FOUND_MESSAGE)));
        ticket.setPriority(ticketPriorityRepository.findById(request.getPriorityId()).orElseThrow(() -> new EntityNotFoundException(PRIORITY_NOT_FOUND_EXCEPTION)));
        ticket.setTitle(request.getTitle());
        ticket.setDescription(request.getDescription());
        ticket.setCreatedAt(LocalDateTime.now());
        ticket.setStatus(new TicketStatus());

       return ticketRepository.save(ticket);
    }
}
