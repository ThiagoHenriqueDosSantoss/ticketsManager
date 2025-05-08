package com.example.ticketsManager.services;


import com.example.ticketsManager.dto.CreateTicketDTO;
import com.example.ticketsManager.entities.Ticket;
import com.example.ticketsManager.entities.User;
import com.example.ticketsManager.repository.TicketRepository;
import com.example.ticketsManager.repository.UserRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.logging.Logger;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;
    private static final Logger logger = Logger.getLogger(TicketService.class.getName());

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public Ticket createTicket(CreateTicketDTO dto){
        try {
            Ticket ticket = new Ticket();
            if (dto.getIdUser() == null) {
                throw new BadRequestException("Informe o usuário que se vinculará ao ticket!");
            }
            User user = userRepository.findById(dto.getIdUser())
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
            ticket.setUser(user);
            if (dto.getQuantidade() == null || dto.getQuantidade() <= 0) {
                throw new BadRequestException("Informe uma quantidade válida de tickets!");
            }
            ticket.setQuantidade(dto.getQuantidade());

            LocalDateTime now = LocalDateTime.now();
            ticket.setDataEntrega(now);

            return ticketRepository.save(ticket);
        } catch (Exception e) {
            logger.severe("ERROR: Falha ao criar um ticket no services! " + e.getMessage());
        }
        return null;
    }
}
