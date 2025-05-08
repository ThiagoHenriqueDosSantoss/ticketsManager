package com.example.ticketsManager.repository;

import com.example.ticketsManager.entities.Ticket;
import com.example.ticketsManager.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

}
