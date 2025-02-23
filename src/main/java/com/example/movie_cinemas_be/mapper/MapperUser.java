package com.example.movie_cinemas_be.mapper;

import com.example.movie_cinemas_be.dtos.request.UserCreateRequest;
import com.example.movie_cinemas_be.dtos.response.UserResponse;
import com.example.movie_cinemas_be.entitys.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MapperUser {
    UserResponse mapToUserResponse(User users);

    User mapToUser(UserCreateRequest userCreateRequest);
}
