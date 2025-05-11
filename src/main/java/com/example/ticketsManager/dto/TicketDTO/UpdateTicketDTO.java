package com.example.ticketsManager.dto.TicketDTO;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public class UpdateTicketDTO {

    private Long idUser;

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

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }
}
