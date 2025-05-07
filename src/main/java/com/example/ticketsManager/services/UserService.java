package com.example.ticketsManager.services;

import com.example.ticketsManager.dto.CreateUserDTO;
import com.example.ticketsManager.entities.User;
import com.example.ticketsManager.enums.UserSituation;
import com.example.ticketsManager.repository.UserRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.logging.Logger;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    private static final Logger logger = Logger.getLogger(UserService.class.getName());

    @Transactional(readOnly = false)
    public User createUser(CreateUserDTO dto) {
        try {
            User users = new User();

            if (dto.getNome() == null || dto.getNome().isBlank()) {
                throw new BadRequestException("O campo nome é obrigatório!");
            }
            if (dto.getNome().length() < 3 || dto.getNome().length() > 30) {
                throw new BadRequestException("O nome deve conter entre 3 e 30 caracteres!");
            }
            if (!dto.getNome().matches("^[A-Za-zÀ-ÿ\\s]+$")) {
                throw new BadRequestException("O nome não pode conter caracteres especiais ou números!");
            }
            users.setNome(dto.getNome());

            if (dto.getCpf() == null || dto.getCpf().isBlank()) {
                throw new BadRequestException("O campo cpf é obrigatório!");
            }
            if (!dto.getCpf().matches("^(\\d{11}|\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2})$")) {
                throw new BadRequestException("O cpf informado é inválido!");
            }
            users.setCpf(dto.getCpf());

            if (dto.getSituacaoUsuario() == null) {
                throw new BadRequestException("A situação do usuário é obrigatória!");
            }
            if (dto.getSituacaoUsuario() != UserSituation.A) {
                throw new BadRequestException("Ao criar usuário a situação não pode ser diferente de A - Ativa!");
            }
            users.setSituacaoUsuario(dto.getSituacaoUsuario());

            LocalDateTime now = LocalDateTime.now();
            users.setDataCriacao(now);

            return userRepository.save(users);
        } catch (Exception e) {
            logger.severe("ERROR: Falha ao criar usuario no services!");
            throw new RuntimeException(e);
        }
    }
}
