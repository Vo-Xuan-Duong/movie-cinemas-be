package com.example.movie_cinemas_be.entitys;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Compani {

    @Id
    private long id;
    private String name;
    private String origin_country;
    private String logo_path;
}
