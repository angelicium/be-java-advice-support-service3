package com.itm.space.service;

import com.itm.space.model.request.CreateTicketRequest;
import com.itm.space.model.response.CreateTicketResponse;

public interface CreateTicketService {

    CreateTicketResponse createAndRetrieveTicket(CreateTicketRequest request);
}
