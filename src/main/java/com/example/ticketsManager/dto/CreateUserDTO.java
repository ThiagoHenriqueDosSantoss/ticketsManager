package com.example.ticketsManager.dto;

import com.example.ticketsManager.enums.UserSituation;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
    private UserSituation situacaoUsuario;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dataCriacao = LocalDateTime.now();

}
