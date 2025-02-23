package com.example.movie_cinemas_be.dtos.response;

import com.example.movie_cinemas_be.reponsitory.PermissionRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PermissionResponse {
    private String name;
    private String description;

}
