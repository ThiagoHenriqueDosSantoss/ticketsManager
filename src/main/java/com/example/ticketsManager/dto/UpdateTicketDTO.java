package com.example.ticketsManager.dto;

import com.example.ticketsManager.entities.User;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public class UpdateTicketDTO {

    @NotBlank
    private Long quantidade;

    private LocalDateTime dtAtualizacaoTicket;

    public Long getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Long quantidade) {
        this.quantidade = quantidade;
    }

    public LocalDateTime getDtAtualizacaoTicket() {
        return dtAtualizacaoTicket;
    }

    public void setDtAtualizacaoTicket(LocalDateTime dtAtualizacaoTicket) {
        this.dtAtualizacaoTicket = dtAtualizacaoTicket;
    }
}
