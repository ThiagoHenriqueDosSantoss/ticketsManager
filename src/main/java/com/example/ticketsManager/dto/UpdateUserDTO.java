package com.example.ticketsManager.dto;

import com.example.ticketsManager.enums.UserSituation;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class UpdateUserDTO {


    @Size(min = 3, max = 30)
    @Pattern(regexp = "^[A-Za-zÀ-ÿ\\\\s]+$", message = "O nome não pode conter caracteres especiais!")
    private String nome;


    @Size(max = 11)
    @Pattern(regexp = "^(\\d{11}|\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2})$", message = "O cpf informado é inválido!")
    private String cpf;

    private String situacaoUsuario;


    public @Size(min = 3, max = 30) @Pattern(regexp = "^[A-Za-zÀ-ÿ\\\\s]+$", message = "O nome não pode conter caracteres especiais!") String getNome() {
        return nome;
    }

    public  @Size(max = 11) @Pattern(regexp = "^(\\d{11}|\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2})$", message = "O cpf informado é inválido!") String getCpf() {
        return cpf;
    }

    public String getSituacaoUsuario() {
        return situacaoUsuario;
    }

    public void setNome(@Size(min = 3, max = 30) @Pattern(regexp = "^[A-Za-zÀ-ÿ\\\\s]+$", message = "O nome não pode conter caracteres especiais!") String nome) {
        this.nome = nome;
    }

    public void setCpf(@Size(max = 11) @Pattern(regexp = "^(\\d{11}|\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2})$", message = "O cpf informado é inválido!") String cpf) {
        this.cpf = cpf;
    }

    public void setSituacaoUsuario(String situacaoUsuario) {
        this.situacaoUsuario = situacaoUsuario;
    }
}
