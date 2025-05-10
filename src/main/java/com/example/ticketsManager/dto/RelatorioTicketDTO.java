package com.example.ticketsManager.dto;

public class RelatorioTicketDTO {
    private String nome;
    private Long totalTickets;

    public RelatorioTicketDTO(String nome, Long totalTickets) {
        this.nome = nome;
        this.totalTickets = totalTickets;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getTotalTickets() {
        return totalTickets;
    }

    public void setTotalTickets(Long totalTickets) {
        this.totalTickets = totalTickets;
    }
}
