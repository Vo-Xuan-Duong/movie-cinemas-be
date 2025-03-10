package com.example.movie_cinemas_be.entitys;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Discount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String code;
    private double quantity;
    private double discountAmount = 0;
    private double discountRate = 0;//giảm theo %
    private Status status;
    private LocalDate createDate;
    public enum Status{
        DANGHOATDONG,
        NGUNGHOATDONG
    }
}
