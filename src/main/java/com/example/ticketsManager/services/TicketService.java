package com.example.ticketsManager.services;


import com.example.ticketsManager.dto.CreateTicketDTO;
import com.example.ticketsManager.dto.UpdateTicketDTO;
import com.example.ticketsManager.entities.Ticket;
import com.example.ticketsManager.entities.User;
import com.example.ticketsManager.repository.TicketRepository;
import com.example.ticketsManager.repository.UserRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.*;
import java.time.LocalDateTime;
import java.util.Optional;
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
    public Ticket uptadeTicket(Long idUser,UpdateTicketDTO dto){
        try {
            User user = userRepository.findById(idUser).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

            Optional<Ticket> optionalTicket = ticketRepository.findById(idUser);


            if (optionalTicket.isEmpty()){
                throw new BadRequestException("O id informado não foi encontrado!");
            }
            Ticket ticket = optionalTicket.get();

            if (dto.getQuantidade() == null || dto.getQuantidade() <= 0) {
                throw new BadRequestException("Informe uma quantidade válida de tickets!");
            }
            ticket.setQuantidade(dto.getQuantidade());

            LocalDateTime now = LocalDateTime.now();
            ticket.setAtualizaoEntregaTicket(now);

            if (!"A".equals(user.getSituacaoUsuario())) {
                throw new RuntimeException("Não é possível criar um ticket para um usuário inativo.");
            }
            return ticketRepository.save(ticket);
        } catch (Exception e) {
            logger.severe("ERROR: Falha ao criar um ticket no services! " + e.getMessage());
        }
        return null;
    }
}
