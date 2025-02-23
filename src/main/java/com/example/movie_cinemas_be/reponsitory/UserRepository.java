package com.example.movie_cinemas_be.reponsitory;

import com.example.movie_cinemas_be.entitys.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
