package com.example.movie_cinemas_be.reponsitory;

import com.example.movie_cinemas_be.entitys.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
