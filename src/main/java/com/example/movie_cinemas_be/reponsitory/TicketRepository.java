package com.example.movie_cinemas_be.reponsitory;

import com.example.movie_cinemas_be.entitys.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    @Query("SELECT t.seat.id FROM Ticket t WHERE t.booking.showTime.id = :showTimeId")
    List<Long> findBookedSeatIdsByShowTime(@Param("showTimeId") Long showTimeId);
}
