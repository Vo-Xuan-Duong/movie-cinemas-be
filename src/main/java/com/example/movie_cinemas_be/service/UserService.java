package com.example.movie_cinemas_be.service;

import com.example.movie_cinemas_be.dtos.request.UserCreateRequest;
import com.example.movie_cinemas_be.dtos.response.UserResponse;
import com.example.movie_cinemas_be.entitys.User;
import com.example.movie_cinemas_be.enums.Role;
import com.example.movie_cinemas_be.exception.CustomException;
import com.example.movie_cinemas_be.exception.ErrorCode;
import com.example.movie_cinemas_be.mapper.MapperUser;
import com.example.movie_cinemas_be.reponsitory.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final MapperUser mapperUser;

    public PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

    public UserService(UserRepository userRepository, MapperUser mapperUser) {
        this.userRepository = userRepository;
        this.mapperUser = mapperUser;
    }

    public UserResponse createUser(UserCreateRequest userCreateRequest) {
        Optional<User> userTest = userRepository.findByUsername(userCreateRequest.getUsername());
        if (userTest.isPresent()) {
            throw new CustomException(ErrorCode.USER_EXISTS_EXCEPTION);
        }
        User users = mapperUser.mapToUser(userCreateRequest);

        users.setPassword(passwordEncoder.encode(users.getPassword()));
//        HashSet<String> roles = new HashSet<>();
//        roles.add(Role.USER.name());
//
//        users.setRoles(roles);
        return mapperUser.mapToUserResponse(userRepository.save(users));
    }

    public UserResponse updateUser(long user_id , UserCreateRequest userCreateRequest) {
        User user_update = userRepository.findById(user_id).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        user_update.setUsername(userCreateRequest.getUsername());
        user_update.setPassword(passwordEncoder.encode(user_update.getPassword()));
//        Set<String> roles = user_update.getRoles();
//        roles.add(user_update.getRoles().iterator().next());
//        user_update.setRoles(roles);

        return  mapperUser.mapToUserResponse(userRepository.save(user_update));
    }


    public UserResponse getUserById(long id) {
        User users = userRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_EXISTS_EXCEPTION));
        return mapperUser.mapToUserResponse(users);
    }

    public UserResponse getUserByUsername(String username) {
        return convertToUserResponse(userRepository.findByUsername(username).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND)));
    }


    public Page<UserResponse> getAllUsers(Pageable pageable) {
        Page<User> users = userRepository.findAll(pageable);
        if (users.isEmpty()) {
            throw new CustomException(ErrorCode.USER_NOT_EXISTS_EXCEPTION);
        }
        return users.map(mapperUser::mapToUserResponse);
    }

    public UserResponse convertToUserResponse(User users) {
        return mapperUser.mapToUserResponse(users);
    }
}
