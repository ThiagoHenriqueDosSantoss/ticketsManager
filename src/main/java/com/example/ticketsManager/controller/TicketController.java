package com.example.ticketsManager.controller;

import com.example.ticketsManager.dto.CreateTicketDTO;
import com.example.ticketsManager.entities.Ticket;
import com.example.ticketsManager.entities.User;
import com.example.ticketsManager.repository.UserRepository;
import com.example.ticketsManager.services.TicketService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
@RequestMapping(value = "/ticket")
public class TicketController {

    @Autowired
    private TicketService ticketService;
    private static final Logger logger = Logger.getLogger(TicketService.class.getName());

    @Autowired
    private UserRepository userRepository;
    @PostMapping
    public ResponseEntity<Ticket> createTicket(@RequestBody CreateTicketDTO dto){
        try{
            Ticket response = ticketService.createTicket(dto);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.severe("Falha ao criar ticket no controller!");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
