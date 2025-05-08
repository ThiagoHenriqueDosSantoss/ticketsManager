package com.example.ticketsManager.dto;

import com.example.ticketsManager.entities.User;
import jakarta.persistence.*;

public class CreateTicketDTO {
    private Long idUser;
    private Long quantidade;

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
}
