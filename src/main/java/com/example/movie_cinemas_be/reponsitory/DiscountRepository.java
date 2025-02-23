package com.example.movie_cinemas_be.reponsitory;

import com.example.movie_cinemas_be.entitys.Discount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DiscountRepository extends JpaRepository<Discount, Long> {
    Discount findDiscountByCode(String code);
}
