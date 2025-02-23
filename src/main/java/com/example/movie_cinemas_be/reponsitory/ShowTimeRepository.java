package com.example.movie_cinemas_be.reponsitory;

import com.example.movie_cinemas_be.entitys.Room;
import com.example.movie_cinemas_be.entitys.ShowTime;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ShowTimeRepository extends JpaRepository<ShowTime, Long> {
    List<ShowTime> findAllByRoomId(long id);
    List<ShowTime> findAllByMovieId(long id);

    boolean existsByRoomAndShowDateAndStartTimeBetween(Room room, LocalDate showDate, LocalTime start, LocalTime end);

    boolean existsByRoomAndShowDateAndEndTimeBetween(Room room, LocalDate showDate, LocalTime start, LocalTime end);

    boolean existsByRoomAndShowDateAndStartTimeLessThanAndEndTimeGreaterThan(
            Room room, LocalDate showDate, LocalTime end, LocalTime start);

    default boolean existsByRoomAndTimeRange(Room room, LocalDate showDate, LocalTime start, LocalTime end) {
        return existsByRoomAndShowDateAndStartTimeBetween(room, showDate, start, end) ||
                existsByRoomAndShowDateAndEndTimeBetween(room, showDate, start, end) ||
                existsByRoomAndShowDateAndStartTimeLessThanAndEndTimeGreaterThan(room, showDate, end, start);
    }

}
