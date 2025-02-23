package com.example.movie_cinemas_be.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateRequest {

    @NotBlank(message = "Username cannot be blank please!")
    private String username;

    @Size(min = 3 , message = "Please FirstName must have at least 3 characters")
    private String firstName;

    private String lastName;

    @NotBlank(message = "Email cannot be blank please!")
    private String email;

    @Size(min = 6, message = "Please Password must have at least 6 characters")
    private String password;
}
