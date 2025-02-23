package com.example.movie_cinemas_be.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

@Service
public class MoMoService {


    public String createPayment(HttpServletRequest request, long amount, String orderinfo){


        return "createPayment Success";
    }

}
