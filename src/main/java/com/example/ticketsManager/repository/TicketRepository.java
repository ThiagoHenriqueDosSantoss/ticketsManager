package com.example.ticketsManager.repository;

import com.example.ticketsManager.entities.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    @Query(value = "SELECT u.nome AS nome, COALESCE(SUM(t.quantidade), 0) AS total_tickets " +
            "FROM tb_user u " +
            "LEFT JOIN tb_ticket t ON u.idUser = t.idUser AND t.dataEntregaTicket <= :dataFim " +
            "GROUP BY u.nome", nativeQuery = true)
    List<Object[]> ticketsByPeriodReport(@Param("dataFim") LocalDateTime dataFim);
}
