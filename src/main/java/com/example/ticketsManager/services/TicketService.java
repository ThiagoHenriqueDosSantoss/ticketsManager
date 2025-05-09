package com.example.ticketsManager.services;


import com.example.ticketsManager.dto.CreateTicketDTO;
import com.example.ticketsManager.dto.UpdateTicketDTO;
import com.example.ticketsManager.entities.Ticket;
import com.example.ticketsManager.entities.User;
import com.example.ticketsManager.repository.TicketRepository;
import com.example.ticketsManager.repository.UserRepository;
import org.apache.coyote.BadRequestException;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class TicketService {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(TicketService.class);
    @Autowired
    private TicketRepository ticketRepository;
    private static final Logger logger = Logger.getLogger(TicketService.class.getName());

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public Ticket createTicket(CreateTicketDTO dto){
        try {
            Ticket ticket = new Ticket();
            try{

                if (dto.getIdUser() == null) {
                    JOptionPane.showMessageDialog(null,"Informe o id do usuario que se vinculará ao ticket!");
                    throw new BadRequestException("Informe o usuário que se vinculará ao ticket!");
                }
                User user = userRepository.findById(dto.getIdUser())
                        .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
                ticket.setUser(user);

                if (dto.getQuantidade() == null || dto.getQuantidade() <= 0) {
                    JOptionPane.showMessageDialog(null,"Informe uma quantidade válida de tickets!");
                    throw new BadRequestException("Informe uma quantidade válida de tickets!");
                }
                ticket.setQuantidade(dto.getQuantidade());

                LocalDateTime now = LocalDateTime.now();
                ticket.setDataEntregaTicket(now);

                if (!"A".equals(user.getSituacaoUsuario())) {
                    JOptionPane.showMessageDialog(null,"Não é possível criar um ticket para um usuário inativo.");
                    throw new RuntimeException("Não é possível criar um ticket para um usuário inativo.");
                }

                JOptionPane.showMessageDialog(null, "Ticket criado com sucesso!");
                return ticketRepository.save(ticket);
            }catch (BadRequestException e){
                JOptionPane.showMessageDialog(null, e.getMessage(), "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            logger.severe("ERROR: Falha ao criar um ticket no services!" + e.getMessage());
        }
        return null;
    }
    @Transactional
    public Ticket updateTicket(Long idTicket, UpdateTicketDTO dto) {
        try {
            Optional<Ticket> optionalTicket = ticketRepository.findById(idTicket);

            if (optionalTicket.isEmpty()) {
                JOptionPane.showMessageDialog(null, "O ticket informado não foi encontrado!");
                return null;
            }
            Ticket ticket = optionalTicket.get();

            if (dto.getIdUser() != null) {
                User user = userRepository.findById(dto.getIdUser()).orElse(null);
                if (user == null) {
                    JOptionPane.showMessageDialog(null, "Usuário não encontrado!");
                    return null;
                }
                if (!"A".equals(user.getSituacaoUsuario())) {
                    JOptionPane.showMessageDialog(null, "Usuário inativo. Não é possível atualizar o ticket.");
                    return null;
                }
                ticket.setUser(user);
            }

            if (dto.getQuantidade() != null) {
                if (dto.getQuantidade() <= 0) {
                    JOptionPane.showMessageDialog(null, "Informe uma quantidade maior que zero!");
                    return null;
                }
                ticket.setQuantidade(dto.getQuantidade());
            }
            ticket.setAtualizaoEntregaTicket(LocalDateTime.now());

            JOptionPane.showMessageDialog(null,"Ticket atualizado com sucesso!");
            return ticketRepository.save(ticket);

        } catch (Exception e) {
            logger.severe("ERROR: Falha ao atualizar o ticket! " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Erro ao atualizar o ticket: " + e.getMessage());
        }
        return null;
    }
    public List<Ticket> listarTicketsAteData(LocalDateTime dataFim) {
        return ticketRepository.buscarTicketsPorDataFim(dataFim);
    }
}
