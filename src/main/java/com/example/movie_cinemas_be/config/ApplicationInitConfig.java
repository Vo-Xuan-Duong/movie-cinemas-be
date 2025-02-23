package com.example.movie_cinemas_be.config;

import com.example.movie_cinemas_be.entitys.User;
import com.example.movie_cinemas_be.enums.Role;
import com.example.movie_cinemas_be.reponsitory.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;

@Configuration
@Slf4j
public class ApplicationInitConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository) {
        return args -> {
            if(userRepository.findByUsername("admin").isEmpty()) {
                var role = new HashSet<String>();
                role.add(Role.ADMIN.name());

                User user = User.builder()
                        .username("admin")
                        .password(passwordEncoder().encode("admin"))
//                        .roles(role)
                        .build();

                userRepository.save(user);
                log.info("Admin user has been created with name admin and password /'admin/' ");
            }
        };
    }

}
