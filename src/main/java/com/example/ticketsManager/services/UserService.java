package com.example.ticketsManager.services;

import com.example.ticketsManager.dto.UserDTO.CreateUserDTO;
import com.example.ticketsManager.dto.UserDTO.UpdateUserDTO;
import com.example.ticketsManager.entities.User;
import com.example.ticketsManager.repository.UserRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
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
                if (dto.getNome() == null || dto.getNome().isBlank()) {
                    JOptionPane.showMessageDialog(null,"O campo nome é obrigatório!");
                    throw new BadRequestException("O campo nome é obrigatório!");
                } else {
                    if (dto.getNome().length() < 3 || dto.getNome().length() > 30) {
                        JOptionPane.showMessageDialog(null,"O nome deve conter entre 3 e 30 caracteres!");
                        throw new BadRequestException("O nome deve conter entre 3 e 30 caracteres!");
                    } else {
                        if (!dto.getNome().matches("^[A-Za-zÀ-ÿ\\s]+$")) {
                            JOptionPane.showMessageDialog(null,"O nome não pode conter caracteres especiais!");
                            throw new BadRequestException("O nome não pode conter caracteres especiais!");
                        } else {
                            users.setNome(dto.getNome());
                        }
                    }
                }

                //Validar campo do cpf

                if (dto.getCpf() == null || dto.getCpf().isBlank()) {
                    JOptionPane.showMessageDialog(null,"O campo cpf é obrigatório!");
                    throw new BadRequestException("O campo cpf é obrigatório!");
                } else {
                    if (!dto.getCpf().matches("^(\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2})$")) {
                        JOptionPane.showMessageDialog(null,"O cpf informado é inválido!");
                        throw new BadRequestException("O cpf informado é inválido!");
                    } else {
                        if (userRepository.validateCpf(dto.getCpf())) {
                            JOptionPane.showMessageDialog(null,"O cpf informado já existe!");
                            throw new BadRequestException("O cpf informado já existe!");
                        } else {
                            users.setCpf(dto.getCpf());
                        }
                    }
                }

                //Validar campo da situação do usuario
                if (dto.getSituacaoUsuario() == null) {
                    JOptionPane.showMessageDialog(null,"A situação do usuário é obrigatória!");
                    throw new BadRequestException("A situação do usuário é obrigatória!");
                } else {
                    if (!"A".equals(dto.getSituacaoUsuario())) {
                        JOptionPane.showMessageDialog(null,"Ao criar usuário a situação não pode ser diferente de A - Ativa!");
                        throw new BadRequestException("Ao criar usuário a situação não pode ser diferente de A - Ativa!");
                    } else {
                        users.setSituacaoUsuario(dto.getSituacaoUsuario());
                    }
                }
                LocalDateTime now = LocalDateTime.now();
                users.setDataCriacao(now);

                JOptionPane.showMessageDialog(null, "Usuário cadastrado com sucesso!");

                return userRepository.save(users);
        } catch (Exception e) {
            logger.severe("ERROR: Falha ao criar usuario no services!");
            throw new RuntimeException(e);
        }
    }
    public User updateUser(Long idUser, UpdateUserDTO dto){
        try {
            Optional<User> userOptional = userRepository.findById(idUser);
                //Valida se o usuario existe
                if (userOptional.isEmpty()) {
                    JOptionPane.showMessageDialog(null,"O id informado não foi encontrado!");
                    throw new BadRequestException("O id informado não foi encontrado!");
                }
                User users = userOptional.get();

                //Valida o nome caso queira alterar
                if (dto.getNome() != null && !dto.getNome().isBlank()) {
                    if (dto.getNome().length() < 3 || dto.getNome().length() > 30) {
                        JOptionPane.showMessageDialog(null,"O nome deve conter entre 3 e 30 caracteres!");
                        throw new BadRequestException("O nome deve conter entre 3 e 30 caracteres!");
                    }else{
                        if (!dto.getNome().matches("^[A-Za-zÀ-ÿ\\s]+$")) {
                            JOptionPane.showMessageDialog(null,"O nome não pode conter caracteres especiais ou números!");
                            throw new BadRequestException("O nome não pode conter caracteres especiais ou números!");
                        }else{
                            users.setNome(dto.getNome());
                        }
                    }
                }
                //Valida o cpf caso queira alterar
                if (dto.getCpf() != null && !dto.getCpf().isBlank()) {
                    if (!dto.getCpf().matches("^(\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2})$")) {
                        JOptionPane.showMessageDialog(null,"O CPF informado é inválido!");
                        throw new BadRequestException("O CPF informado é inválido!");
                    }else{
                        if (userRepository.validateCpf(dto.getCpf())) {
                            JOptionPane.showMessageDialog(null,"O CPF informado já existe!");
                            throw new BadRequestException("O CPF informado já existe!");
                        }else{
                            users.setCpf(dto.getCpf());
                        }
                    }
                }

                //Verifica a situação caso queira alterar
                if (dto.getSituacaoUsuario() == null) {
                    dto.getSituacaoUsuario();
                }else{
                    users.setSituacaoUsuario(dto.getSituacaoUsuario());
                }

                LocalDateTime now = LocalDateTime.now();
                users.setDataAlteracao(now);

                JOptionPane.showMessageDialog(null, "Usuário atualizado com sucesso!");
                return userRepository.save(users);
        } catch (Exception e) {
            logger.severe("ERROR: Falha ao atualizar usuario no services!");
            throw new RuntimeException(e);
        }
    }
    public List<User> listUser(){
        try{
            List<User> userList = userRepository.listUsers();
            return userList;
        } catch (Exception e) {
            logger.severe("ERROR: Falha ao listar usuario no services!");
        }
        return null;
    }
}
