package com.example.movie_cinemas_be.reponsitory;

import com.example.movie_cinemas_be.entitys.Booking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;




@Repository
public interface BookingReponsitory extends JpaRepository<Booking, Long> {
    Booking findBookingByBookingCode(String bookingCode);
    Page<Booking> findAll(Pageable pageable);
}
