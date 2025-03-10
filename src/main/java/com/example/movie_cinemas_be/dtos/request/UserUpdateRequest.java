package com.example.movie_cinemas_be.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateRequest {

    @NotBlank(message = "Username cannot be blank please!")
    private String username;

    private String phone;

    @Size(min = 6, message = "Please Password must have at least 6 characters")
    private String password;

    private List<Long> roleId;
}
