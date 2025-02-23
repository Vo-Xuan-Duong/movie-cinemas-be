package com.example.movie_cinemas_be.controller;

import com.example.movie_cinemas_be.dtos.ResponseCustom;
import com.example.movie_cinemas_be.dtos.request.AuthenticationRequest;
import com.example.movie_cinemas_be.dtos.request.IntrospectRequest;
import com.example.movie_cinemas_be.dtos.response.AuthenticationResponse;
import com.example.movie_cinemas_be.dtos.response.IntrospectResponse;
import com.example.movie_cinemas_be.service.AuthenticationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("token")
    public ResponseCustom<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest authenticationRequest) {
         var result = authenticationService.authenticate(authenticationRequest);
         return ResponseCustom.<AuthenticationResponse>builder()
                 .message("Generated token successfully")
                 .data(result)
                 .build();
    }

    @PostMapping("/introspect")
    public ResponseCustom<IntrospectResponse> introspec(@RequestBody IntrospectRequest introspectRequest) {
        IntrospectResponse result = authenticationService.introspect(introspectRequest);
        return ResponseCustom.<IntrospectResponse>builder()
                .message(result.isValid() ? "Token is valid and has remaining time" : "Token is invalid or expired")
                .data(result)
                .build();
    }
}
