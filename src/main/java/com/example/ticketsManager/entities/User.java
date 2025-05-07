package com.example.ticketsManager.entities;

import com.example.ticketsManager.enums.UserSituation;
import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tb_user")
public class User {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id_user", updatable = false, nullable = false)
    private UUID idUser;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String cpf;

    @Column(nullable = false)
    private UserSituation situacaoUsuario;

    private LocalDateTime dataCriacao;

    private LocalDateTime dataAlteracao;

    public User(){

    }

    public User(UUID idUser, String nome, String cpf, UserSituation situacaoUsuario, LocalDateTime dataCriacao, LocalDateTime dataAlteracao) {
        this.idUser = idUser;
        this.nome = nome;
        this.cpf = cpf;
        this.situacaoUsuario = situacaoUsuario;
        this.dataCriacao = dataCriacao;
        this.dataAlteracao = dataAlteracao;
    }

    public UUID getIdUser() {
        return idUser;
    }

    public void setIdUser(UUID idUser) {
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

    public UserSituation getSituacaoUsuario() {
        return situacaoUsuario;
    }

    public void setSituacaoUsuario(UserSituation situacaoUsuario) {
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
