package com.example.movie_cinemas_be.controller;

import com.example.movie_cinemas_be.dtos.ResponseCustom;
import com.example.movie_cinemas_be.dtos.request.UserCreateRequest;
import com.example.movie_cinemas_be.dtos.response.UserResponse;
import com.example.movie_cinemas_be.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseCustom<UserResponse> createNewUser(@RequestBody @Valid UserCreateRequest userCreateRequest) {
        return ResponseCustom.<UserResponse>builder()
                .code(HttpStatus.CREATED.value())
                .message("Successfully created a new user")
                .data(userService.createUser(userCreateRequest))
                .build();
    }

    @GetMapping("/allUsers")
    public ResponseCustom<List<UserResponse>> getAllUsers() {
        return ResponseCustom.<List<UserResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("Successfully retrieved all users")
                .data(userService.getAllUsers())
                .build();
    }

    @GetMapping("/getUser/{user_id}")
    public ResponseCustom<UserResponse> getUser(@PathVariable("user_id") Long user_id) {
        return ResponseCustom.<UserResponse>builder()
                .message("Successfully retrieved user")
                .data(userService.getUserById(user_id))
                .build();
    }

    @GetMapping("/myInfo")
    public ResponseCustom<UserResponse> getMyInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ResponseCustom.<UserResponse>builder()
                .message("Successfully retrieved user")
                .data(userService.getUserByUsername(authentication.getName()))
                .build();
    }


//    @PutMapping("/update")
//    public ResponseCustom<UserResponse> updateUser(@RequestBody @Valid UserCreateRequest userCreateRequest) {
//        return ResponseCustom.<UserResponse>builder()
//                .message("Successfully updated user")
//                .data(userService.)
//                .build();
//    }

}
