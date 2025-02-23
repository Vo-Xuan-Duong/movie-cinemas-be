package com.example.movie_cinemas_be.dtos.response;

import com.example.movie_cinemas_be.entitys.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Set<Role> roles;
}
