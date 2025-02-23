package com.example.movie_cinemas_be.entitys;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "Payments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToOne
    @JoinColumn(name = "booking_id", nullable = true)
    private Booking booking;

//    @OneToOne
//    @JoinColumn(name = "order_id", nullable = true)
//    private Order order;

    @Column(nullable = false)
    private double amount;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status = PaymentStatus.PENDING;

    public enum PaymentStatus {
        PENDING, SUCCESSFUL, FAILED
    }

    public enum PaymentMethod {
        CREDIT_CARD, PAYPAL, MOMO, ZALO_PAY
    }
}

