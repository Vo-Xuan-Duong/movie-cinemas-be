//package com.example.movie_cinemas_be.entitys;
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import java.util.List;
//
//@Entity
//@Table(name = "Orders")
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//public class Order {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @ManyToOne
//    @JoinColumn(name = "user_id", nullable = false)
//    private User user;
//
//    @Column(nullable = false)
//    private double totalPrice;
//
//    @Enumerated(EnumType.STRING)
//    private OrderStatus status = OrderStatus.PENDING;
//
//    @OneToOne(mappedBy = "order")
//    private Payment payment;
//
//    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
//    private List<OrderItem> orderItems;
//
//    public enum OrderStatus {
//        PENDING, CONFIRMED, CANCELLED
//    }
//}
