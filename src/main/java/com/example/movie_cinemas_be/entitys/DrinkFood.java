//package com.example.movie_cinemas_be.entitys;
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//@Entity
//@Table(name = "DrinkFood")
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//public class DrinkFood {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(nullable = false)
//    private String name;
//
//    @Column(nullable = false)
//    private double price;
//
//    @Enumerated(EnumType.STRING)
//    private Category category;
//
//    public enum Category {
//        DRINK, SNACK, COMBO
//    }
//
//}
