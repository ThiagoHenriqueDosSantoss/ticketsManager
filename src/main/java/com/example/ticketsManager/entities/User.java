package com.example.ticketsManager.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "tb_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "iduser", updatable = false, nullable = false)
    private Long idUser;

    @Column(name = "nome",nullable = false)
    private String nome;

    @Column(name = "cpf",nullable = false, unique = true)
    private String cpf;

    @Column(name = "situacaousuario",nullable = false)
    private String situacaoUsuario;

    @Column(name = "datacriacao")
    private LocalDateTime dataCriacao;

    @Column(name = "dataatualizacao")
    private LocalDateTime dataAlteracao;

    @OneToMany(mappedBy = "idUser")
    private List<Ticket> tickets;

    public User(){

    }


    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getSituacaoUsuario() {
        return situacaoUsuario;
    }

    public void setSituacaoUsuario(String situacaoUsuario) {
        this.situacaoUsuario = situacaoUsuario;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public LocalDateTime getDataAlteracao() {
        return dataAlteracao;
    }

    public void setDataAlteracao(LocalDateTime dataAlteracao) {
        this.dataAlteracao = dataAlteracao;
    }
}
