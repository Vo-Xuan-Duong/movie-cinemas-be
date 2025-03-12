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
                registry.addMapping("/api/**") // Cho phép API truy cập
                        .allowedOrigins("http://127.0.0.1:5501") // Chỉ cho phép từ frontend này
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Các phương thức HTTP
                        .allowedHeaders("*") // Cho phép mọi header
                        .allowCredentials(true); // Nếu có đăng nhập, cookie
            }
        };
    }
}
