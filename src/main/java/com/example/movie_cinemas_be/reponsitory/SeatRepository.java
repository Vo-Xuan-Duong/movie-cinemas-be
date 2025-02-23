package com.example.movie_cinemas_be.reponsitory;

import com.example.movie_cinemas_be.entitys.Room;
import com.example.movie_cinemas_be.entitys.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SeatRepository extends JpaRepository<Seat, Long> {
    List<Seat> findByRoom(Room room);

    @Query("SELECT s FROM Seat s WHERE s.room.id = :roomId")
    List<Seat> findSeatsByRoom(@Param("roomId") Long roomId);

    List<Seat> findByIdIn(List<Long> ids);

}
