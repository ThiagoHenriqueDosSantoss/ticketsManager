package com.example.ticketsManager.repository;

import com.example.ticketsManager.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {


    @Query("SELECT COUNT(u) > 0 FROM User u WHERE u.cpf = :cpf")
    boolean validaCpf(@Param("cpf") String cpf);
}
