package com.example.ticketsManager.controller;

import com.example.ticketsManager.dto.TicketDTO.CreateTicketDTO;
import com.example.ticketsManager.dto.RelatorioTicketDTO;
import com.example.ticketsManager.dto.TicketDTO.UpdateTicketDTO;
import com.example.ticketsManager.entities.Ticket;
import com.example.ticketsManager.repository.UserRepository;
import com.example.ticketsManager.services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
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

    @PatchMapping(value = "/{idTicket}")
    public ResponseEntity<Ticket> updateTicket(@PathVariable Long idTicket, @RequestBody UpdateTicketDTO dto){
        try{
            Ticket response = ticketService.updateTicket(idTicket,dto);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.severe("ERROR: Falha ao atualizar ticket em controller!" + e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
    public ResponseEntity<List<RelatorioTicketDTO>> gerarRelatorio(
            @RequestParam("dataFim") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataFim) {
            List<RelatorioTicketDTO> relatorio = ticketService.gerarRelatorio(dataFim);
        return ResponseEntity.ok(relatorio);
    }
}
