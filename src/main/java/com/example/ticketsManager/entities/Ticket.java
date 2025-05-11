package com.example.ticketsManager.entities;


import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_ticket")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idticket", updatable = false, nullable = false)
    private Long idTicket;

    @ManyToOne
    @JoinColumn(name = "iduser", nullable = false, referencedColumnName = "iduser")
    private User idUser;

    @Column(name = "numticket")
    private Long numTicket;

    @Column(name = "quantidade", nullable = false)
    private Long quantidade;

    @Column(name = "dataentregaticket")
    private LocalDateTime dataEntregaTicket;

    @Column(name = "atualizacaoentregaticket")
    private LocalDateTime atualizaoEntregaTicket;

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

    public LocalDateTime getDataEntregaTicket() {
        return dataEntregaTicket;
    }

    public void setDataEntregaTicket(LocalDateTime dataEntrega) {
        this.dataEntregaTicket = dataEntrega;
    }

    public LocalDateTime getAtualizaoEntregaTicket() {
        return atualizaoEntregaTicket;
    }

    public void setAtualizaoEntregaTicket(LocalDateTime atualizaoEntregaTicket) {
        this.atualizaoEntregaTicket = atualizaoEntregaTicket;
    }

    public Long getNumTicket() {
        return numTicket;
    }

    public void setNumTicket(Long numTicket) {
        this.numTicket = numTicket;
    }
}
