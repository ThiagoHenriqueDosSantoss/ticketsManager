package com.example.ticketsManager.services;


import com.example.ticketsManager.dto.TicketDTO.CreateTicketDTO;
import com.example.ticketsManager.dto.RelatorioTicketDTO;
import com.example.ticketsManager.dto.TicketDTO.UpdateTicketDTO;
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
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.logging.Logger;
import java.util.stream.Collectors;

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
                }else{
                    ticket.setQuantidade(dto.getQuantidade());
                }

                LocalDateTime now = LocalDateTime.now();
                ticket.setDataEntregaTicket(now);

                //Gera um numero aleatório para o ticket
                Random random = new Random();
                Long numTicket = random.nextLong(1000);
                ticket.setNumTicket(numTicket);

                if (!"A".equals(user.getSituacaoUsuario())) {
                    JOptionPane.showMessageDialog(null,"Não é possível criar um ticket para um usuário inativo.");
                    throw new RuntimeException("Não é possível criar um ticket para um usuário inativo.");
                }

                JOptionPane.showMessageDialog(null, "Ticket criado com sucesso!");
                return ticketRepository.save(ticket);
        } catch (Exception e) {
            logger.severe("ERROR: Falha ao criar um ticket no services!" + e.getMessage());
        }
        return null;
    }

    @Transactional
    public Ticket updateTicket(Long idTicket, UpdateTicketDTO dto) {
        try {
            Optional<Ticket> optionalTicket = ticketRepository.findById(idTicket);

            //Valida se o ticket existe
            if (optionalTicket.isEmpty()) {
                JOptionPane.showMessageDialog(null, "O ticket informado não foi encontrado!");
                return null;
            }
            Ticket ticket = optionalTicket.get();

            //Verifica sobre o usuario
            if (dto.getIdUser() != null) {
                User user = userRepository.findById(dto.getIdUser()).orElse(null);
                if (user == null) {
                    JOptionPane.showMessageDialog(null, "Usuário não encontrado!");
                    return null;
                }
                if (!"A".equals(user.getSituacaoUsuario())) {
                    JOptionPane.showMessageDialog(null, "Usuário inativo. Não é possível vincular o ticket a ele.");
                    return null;
                }else{
                    ticket.setUser(user);
                }
            }

            //Verifica a quantidade
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

    public List<RelatorioTicketDTO> gerarRelatorio(LocalDateTime dataFim) {
        List<Object[]> resultado = ticketRepository.relatorioTicketsPorPeriodo(dataFim);

        return resultado.stream()
                .map(linha -> new RelatorioTicketDTO(
                        (String) linha[0],
                        ((Number) linha[1]).longValue()
                ))
                .collect(Collectors.toList());
    }

    public List<Ticket> listTickets() {
        try {
            List<Ticket> ticketList = ticketRepository.findAll();
            return ticketList != null ? ticketList : Collections.emptyList();
        } catch (Exception e) {
            logger.severe("ERRO: Falha ao listar tickets no services!");
            return Collections.emptyList(); // <- evita o NullPointerException
        }
    }
}
