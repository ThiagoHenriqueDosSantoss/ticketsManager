package com.example.ticketsManager.dto.TicketDTO;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public class CreateTicketDTO {

    @NotBlank
    private Long idUser;

    @NotBlank
    private Long quantidade;

    private LocalDateTime dataEntregaTicket;

    public Long getIdUser() {
        return idUser;
    }

    public Long getQuantidade() {
        return quantidade;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public void setQuantidade(Long quantidade) {
        this.quantidade = quantidade;
    }

    public LocalDateTime getDataEntregaTicket() {
        return dataEntregaTicket;
    }

    public void setDataEntregaTicket(LocalDateTime dataEntregaTicket) {
        this.dataEntregaTicket = dataEntregaTicket;
    }
}
