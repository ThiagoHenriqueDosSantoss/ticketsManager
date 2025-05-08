package com.example.ticketsManager.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "tb_ticket")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ticket", updatable = false, nullable = false)
    private Long idTicket;

    @ManyToOne
    @JoinColumn(name = "id_user", nullable = false)
    private User idUser;

    private Long quantidade;

    public Ticket(){

    }

    public Long getIdTicket() {
        return idTicket;
    }

    public void setIdTicket(Long idTicket) {
        this.idTicket = idTicket;
    }

    public User getUser() {
        return idUser;
    }

    public void setUser(User idUser) {
        this.idUser = idUser;
    }

    public Long getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Long quantidade) {
        this.quantidade = quantidade;
    }
}
