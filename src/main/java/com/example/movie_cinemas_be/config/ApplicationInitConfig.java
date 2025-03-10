package com.example.movie_cinemas_be.config;

import com.example.movie_cinemas_be.entitys.Role;
import com.example.movie_cinemas_be.entitys.User;
import com.example.movie_cinemas_be.reponsitory.RoleRepository;
import com.example.movie_cinemas_be.reponsitory.UserRepository;
import com.example.movie_cinemas_be.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Configuration
@Slf4j
public class ApplicationInitConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository, RoleService roleService, RoleRepository roleRepository) {
        return args -> {
            if(userRepository.findByUsername("admin").isEmpty()) {
                Role role = roleRepository.findByName("Admin");
                List<Role> roles = new ArrayList<>();
                roles.add(role);

                User user = User.builder()
                        .username("admin")
                        .password(passwordEncoder().encode("admin"))
                        .roles(roles)
                        .build();

                userRepository.save(user);
                log.info("Admin user has been created with name admin and password /'admin/' ");
            }
        };
    }

}
