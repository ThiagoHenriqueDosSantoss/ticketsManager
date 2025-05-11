package com.example.ticketsManager.dto.UserDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class CreateUserDTO {


    @NotBlank(message = "O campo nome é obrigatório!")
    @Size(min = 3, max = 30)
    @Pattern(regexp = "^[A-Za-zÀ-ÿ\\\\s]+$", message = "O nome não pode conter caracteres especiais!")
    private String nome;


    @NotBlank(message = "O campo cpf é obrigatório!")
    @Size(max = 11)
    @Pattern(regexp = "^(\\d{11}|\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2})$", message = "O cpf informado é inválido!")
    private String cpf;

    @NotBlank
    @Size(max = 1)
    private String situacaoUsuario;

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
}
