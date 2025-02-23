//package com.example.movie_cinemas_be.entitys;
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//@Entity
//@Table(name = "Order_Items")
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//public class OrderItem {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @ManyToOne
//    @JoinColumn(name = "order_id", nullable = false)
//    private Order order;
//
//    @ManyToOne
//    @JoinColumn(name = "drink_food_id", nullable = false)
//    private DrinkFood drinkFood;
//
//    @Column(nullable = false)
//    private int quantity;
//
//    @Column(nullable = false)
//    private double price;
//}
//
