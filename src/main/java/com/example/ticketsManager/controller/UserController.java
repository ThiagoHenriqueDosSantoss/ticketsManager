package com.example.ticketsManager.controller;

import com.example.ticketsManager.dto.CreateUserDTO;
import com.example.ticketsManager.dto.UpdateUserDTO;
import com.example.ticketsManager.entities.User;
import com.example.ticketsManager.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.logging.Logger;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    private static final Logger logger = Logger.getLogger(UserController.class.getName());


    public ResponseEntity<User> createUser(CreateUserDTO dto){
        try{
            User response = userService.createUser(dto);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.severe("ERROR: Falha ao criar usuario em controller!" + e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    public ResponseEntity<User> createUser( Long idUser,UpdateUserDTO dto){
        try{
            User response = userService.updateUser(idUser,dto);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.severe("ERROR: Falha ao atualizar usuario em controller!" + e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
