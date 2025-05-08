package com.example.ticketsManager.dto;

import com.example.ticketsManager.entities.User;

public class UpdateTicketDTO {
    private Long idUser;

    private Long quantidade;

    public Long getIdUser() {
        return idUser;
    }

    public Long getQuantidade() {
        return quantidade;
    }
}
