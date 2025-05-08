package com.example.ticketsManager.dto;

import com.example.ticketsManager.entities.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

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
