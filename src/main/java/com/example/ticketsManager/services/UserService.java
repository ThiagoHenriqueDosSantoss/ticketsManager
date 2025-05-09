package com.example.ticketsManager.services;

import com.example.ticketsManager.dto.CreateUserDTO;
import com.example.ticketsManager.dto.UpdateUserDTO;
import com.example.ticketsManager.entities.User;
import com.example.ticketsManager.enums.UserSituation;
import com.example.ticketsManager.repository.UserRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.*;
import java.time.LocalDateTime;
import java.util.Optional;
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
            //Validar campo do nome
            try {
                if (dto.getNome() == null || dto.getNome().isBlank()) {
                    throw new BadRequestException("O campo nome é obrigatório!");
                } else {
                    if (dto.getNome().length() < 3 || dto.getNome().length() > 30) {
                        throw new BadRequestException("O nome deve conter entre 3 e 30 caracteres!");
                    } else {
                        if (!dto.getNome().matches("^[A-Za-zÀ-ÿ\\s]+$")) {
                            throw new BadRequestException("O nome não pode conter caracteres especiais!");
                        } else {
                            users.setNome(dto.getNome());
                        }
                    }
                }

                //Validar campo do cpf

                if (dto.getCpf() == null || dto.getCpf().isBlank()) {
                    throw new BadRequestException("O campo cpf é obrigatório!");
                } else {
                    if (!dto.getCpf().matches("^(\\d{11}|\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2})$")) {
                        throw new BadRequestException("O cpf informado é inválido!");
                    } else {
                        if (userRepository.validaCpf(dto.getCpf())) {
                            throw new BadRequestException("O cpf informado já existe!");
                        } else {
                            users.setCpf(dto.getCpf());
                        }
                    }
                }

                //Validar campo da situação do usuario
                if (dto.getSituacaoUsuario() == null) {
                    throw new BadRequestException("A situação do usuário é obrigatória!");
                } else {
                    if (!"A".equals(dto.getSituacaoUsuario())) {
                        throw new BadRequestException("Ao criar usuário a situação não pode ser diferente de A - Ativa!");
                    } else {
                        users.setSituacaoUsuario(dto.getSituacaoUsuario());
                    }
                }
                LocalDateTime now = LocalDateTime.now();
                users.setDataCriacao(now);

                JOptionPane.showMessageDialog(null, "Usuário cadastrado com sucesso!");

                return userRepository.save(users);
            } catch (BadRequestException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            logger.severe("ERROR: Falha ao criar usuario no services!");
            throw new RuntimeException(e);
        }
        return null;
    }
    public User updateUser(Long idUser, UpdateUserDTO dto){
        try {
            Optional<User> userOptional = userRepository.findById(idUser);
            try {
                if (userOptional.isEmpty()) {
                    throw new BadRequestException("O id informado não foi encontrado!");
                }
                User users = userOptional.get();

                if (dto.getNome() != null && !dto.getNome().isBlank()) {
                    if (dto.getNome().length() < 3 || dto.getNome().length() > 30) {
                        throw new BadRequestException("O nome deve conter entre 3 e 30 caracteres!");
                    }
                    if (!dto.getNome().matches("^[A-Za-zÀ-ÿ\\s]+$")) {
                        throw new BadRequestException("O nome não pode conter caracteres especiais ou números!");
                    }
                    users.setNome(dto.getNome());
                }

                if (dto.getCpf() != null && !dto.getCpf().isBlank()) {
                    if (!dto.getCpf().matches("^(\\d{11}|\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2})$")) {
                        throw new BadRequestException("O CPF informado é inválido!");
                    }
                    if (userRepository.validaCpf(dto.getCpf())) {
                        throw new BadRequestException("O CPF informado já existe!");
                    }
                    users.setCpf(dto.getCpf());
                }

                if (dto.getSituacaoUsuario() != null) {
                    users.setSituacaoUsuario(dto.getSituacaoUsuario());
                } else {
                    if ("A".equals(dto.getSituacaoUsuario())) {
                        users.setSituacaoUsuario("A");
                    } else {
                        users.setSituacaoUsuario("I");
                    }
                }

                LocalDateTime now = LocalDateTime.now();
                users.setDataAlteracao(now);

                return userRepository.save(users);
            }catch (BadRequestException e){
                JOptionPane.showMessageDialog(null, e.getMessage(), "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            logger.severe("ERROR: Falha ao atualizar usuario no services!");
            throw new RuntimeException(e);
        }
        return null;
    }
}
