package com.example.movie_cinemas_be.controller;

import com.example.movie_cinemas_be.dtos.ResponseCustom;
import com.example.movie_cinemas_be.dtos.request.TicketRequest;
import com.example.movie_cinemas_be.dtos.response.TicketResponse;
import com.example.movie_cinemas_be.reponsitory.TicketRepository;
import com.example.movie_cinemas_be.service.TicketService;
import lombok.Getter;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ticket")
public class TicketController {
    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping("/{booking_id}")
    public ResponseCustom<TicketResponse> createTicket(@PathVariable long booking_id, @RequestBody TicketRequest ticketRequest) {
        return ResponseCustom.<TicketResponse>builder()
                .message("Ticket created successfully")
                .data(ticketService.createTicket(booking_id, ticketRequest))
                .build();
    }

    @GetMapping("/ticket_id")
    public ResponseCustom<TicketResponse> getTicket(@PathVariable long ticket_id) {
        return ResponseCustom.<TicketResponse>builder()
                .message("Ticket found")
                .data(ticketService.getTicketById(ticket_id))
                .build();
    }

    @GetMapping
    public ResponseCustom<List<TicketResponse>> getAllTickets() {
        return ResponseCustom.<List<TicketResponse>>builder()
                .message("Tickets get all successfully")
                .data(ticketService.getAllTickets())
                .build();
    }

    @DeleteMapping("/ticket_id")
    public ResponseCustom<Void> deleteTicket(@PathVariable long ticket_id) {
        ticketService.deleteTicket(ticket_id);
        return ResponseCustom.<Void>builder()
                .message("Ticket deleted successfully")
                .build();
    }
}
