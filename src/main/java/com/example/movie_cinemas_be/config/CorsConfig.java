package com.example.movie_cinemas_be.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**")  // Cho phép tất cả API có đường dẫn /api/**
                        .allowedOrigins("http://127.0.0.1:5501")  // Cho phép frontend truy cập
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")  // Cho phép các phương thức HTTP
                        .allowedHeaders("*")  // Cho phép tất cả headers
                        .allowCredentials(true);  // Cho phép gửi cookies
            }
        };
    }
}
