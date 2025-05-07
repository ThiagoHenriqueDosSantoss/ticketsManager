package com.example.ticketsManager.controller;

import com.example.ticketsManager.dto.CreateUserDTO;
import com.example.ticketsManager.entities.User;
import com.example.ticketsManager.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
@RequestMapping(value = "/user")

public class UserController {

    @Autowired
    private UserService userService;

    private static final Logger logger = Logger.getLogger(UserController.class.getName());

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody CreateUserDTO dto){
        try{
            User response = userService.createUser(dto);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.severe("ERROR: Falha ao criar usuario em controller!" + e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

}
