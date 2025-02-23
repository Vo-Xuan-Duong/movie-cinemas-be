package com.example.movie_cinemas_be.service;

import com.example.movie_cinemas_be.dtos.request.ShowTimeRequest;
import com.example.movie_cinemas_be.dtos.response.CinemaFromShowtime;
import com.example.movie_cinemas_be.dtos.response.GroupedCinemaResponse;
import com.example.movie_cinemas_be.dtos.response.ShowTimeResponse;
import com.example.movie_cinemas_be.entitys.Movie;
import com.example.movie_cinemas_be.entitys.Room;
import com.example.movie_cinemas_be.entitys.ShowTime;
import com.example.movie_cinemas_be.entitys.Ticket;
import com.example.movie_cinemas_be.exception.CustomException;
import com.example.movie_cinemas_be.exception.ErrorCode;
import com.example.movie_cinemas_be.mapper.MapperMovie;
import com.example.movie_cinemas_be.reponsitory.MovieRepository;
import com.example.movie_cinemas_be.reponsitory.RoomRepository;
import com.example.movie_cinemas_be.reponsitory.ShowTimeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ShowTimeService {

    private final ShowTimeRepository showTimeRepository;
    private final MovieRepository movieRepository;
    private final RoomRepository roomRepository;
    private final CinemasService cinemasService;
    private final RoomService roomService;
    private final MovieService movieService;
    private final MapperMovie mapperMovie;

    public ShowTimeService(
            ShowTimeRepository showTimeRepository,
            MovieRepository movieRepository,
            RoomRepository roomRepository, CinemasService cinemasService, RoomService roomService, MovieService movieService, MapperMovie mapperMovie) {
        this.showTimeRepository = showTimeRepository;
        this.movieRepository = movieRepository;
        this.roomRepository = roomRepository;
        this.cinemasService = cinemasService;
        this.roomService = roomService;
        this.movieService = movieService;
        this.mapperMovie = mapperMovie;
    }

    public Page<ShowTimeResponse> getAllShowTimesByRoomId(long roomId, Pageable pageable) {
        Page<ShowTime> showTimes = showTimeRepository.findShowTimeByRoom_Id(roomId, pageable);
        return showTimes.map(showtime -> convertToShowTimeResponse(showtime));
    }

    public List<GroupedCinemaResponse> getAllShowTimesByMovieId(long movieId) {
            List<ShowTime> showTimes = showTimeRepository.findAllByMovieId(movieId);
         List<ShowTimeResponse> showTimeResponses = showTimes.stream().map(showTimes1 -> convertToShowTimeResponse(showTimes1)).collect(Collectors.toList());

        Map<Long ,List<ShowTimeResponse>> groupedByCinema = showTimeResponses.stream()
                .collect(Collectors.groupingBy(st -> st.getCinema().getCinema_id()));

        return groupedByCinema.entrySet().stream()
                .map(entry -> {
                    List<ShowTimeResponse> sorted = entry.getValue().stream()
                            .sorted(Comparator.comparing(ShowTimeResponse::getStart_time)).collect(Collectors.toList());

                    CinemaFromShowtime cinema = sorted.get(0).getCinema();

                    return new GroupedCinemaResponse(cinema.getCinema_id(), cinema.getCinema_name(), cinema.getCinema_address(), cinema.getCinema_address_map(), sorted);
                })
                .collect(Collectors.toList());
    }

    public Page<ShowTimeResponse> getAllShowTimes(Pageable pageable){
        Page<ShowTime> showTimeResponseList = showTimeRepository.findAll(pageable);
        return showTimeResponseList.map(showTimes -> convertToShowTimeResponse(showTimes));
    }

    public ShowTimeResponse addShowTime(ShowTimeRequest request) {
        log.warn("movie_id: " + request.getMovieId() + ", room_id: " + request.getRoomId());
        Room room = roomRepository.findById(request.getRoomId()).orElseThrow(() -> new CustomException(ErrorCode.ROOMS_NOT_FOUND));
        Movie movie = movieRepository.findById(request.getMovieId()).orElseThrow(() -> new CustomException(ErrorCode.MOVIE_NOT_FOUND));

        boolean isOverlap = showTimeRepository.existsByRoomAndTimeRange(
                room,request.getShowDate() ,request.getStartTime(), request.getStartTime().plusMinutes(movie.getDuration()));

        if (isOverlap) {
            throw new CustomException(ErrorCode.SHOWTIME_CONFLICT);
        }


        ShowTime showTime = new ShowTime();
        showTime.setRoom(room);
        showTime.setMovie(movie);
        showTime.setShowDate(request.getShowDate());
        showTime.setStartTime(request.getStartTime());
        showTime.setEndTime(request.getStartTime().plusMinutes(movie.getDuration()));
        showTime.setPrice(request.getPrice());

        return convertToShowTimeResponse(showTimeRepository.save(showTime));
    }

    public ShowTimeResponse updateShowTime(long showTime_id , ShowTimeRequest request) {
        Room room = roomRepository.findById(request.getRoomId()).orElseThrow(() -> new CustomException(ErrorCode.ROOMS_NOT_FOUND));
        Movie movie = movieRepository.findById(request.getMovieId()).orElseThrow(() -> new CustomException(ErrorCode.MOVIE_NOT_FOUND));
        ShowTime showTime = showTimeRepository.findById(showTime_id).orElseThrow(() -> new CustomException(ErrorCode.SHOWTIME_NOT_FOUND));

        boolean isOverlap = showTimeRepository.existsByRoomAndTimeRange(
                room,request.getShowDate() ,request.getStartTime(), request.getStartTime().plusMinutes(movie.getDuration()));

        if (isOverlap) {
            throw new CustomException(ErrorCode.SHOWTIME_CONFLICT);
        }

        showTime.setRoom(room);
        showTime.setMovie(movie);
        showTime.setShowDate(request.getShowDate());
        showTime.setStartTime(request.getStartTime());
        showTime.setEndTime(request.getStartTime().plusMinutes(movie.getDuration()));
        showTime.setPrice(request.getPrice());

        return convertToShowTimeResponse(showTimeRepository.save(showTime));
    }



    public void deleteShowTime(long showTime_id){
        showTimeRepository.deleteById(showTime_id);
    }

    //Tính năng nâng cấp sau
    public void dynamicDeleteShowTime(){

    }



    public ShowTimeResponse convertToShowTimeResponse(ShowTime showTime) {
        return ShowTimeResponse.builder()
                .id(showTime.getId())
                .cinema(cinemasService.convertCinemasToCinema(showTime.getRoom().getCinema()))
                .room(roomService.convertToRoomResponse(showTime.getRoom()))
                .movie(mapperMovie.mapToMovieResponse(showTime.getMovie()))
                .show_date(showTime.getShowDate())
                .start_time(showTime.getStartTime())
                .end_time(showTime.getEndTime())
                .price(showTime.getPrice())
                .build();
    }
}
