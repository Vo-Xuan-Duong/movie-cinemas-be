package com.example.movie_cinemas_be.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

import javax.crypto.spec.SecretKeySpec;

@Configuration
public class SecurityConfig {

    @Value("${jwt.signerKey}")
    private String signerKey;

    private final String[] PUBLIC_ENDPOINTS = {
            "/api/v1/**",
            "/swagger-ui/**"

    };

    private final String[] PRIVATE_ENDPOINTS = {
            "/api/v1/user/allUsers"
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable());
        http.authorizeHttpRequests(authorizeRequests -> authorizeRequests
//                        .requestMatchers(PUBLIC_ENDPOINTS).permitAll()
//                        .requestMatchers(PRIVATE_ENDPOINTS).hasAnyAuthority("ROLE_ADMIN")
                        .anyRequest().permitAll()

        );
        http.oauth2ResourceServer(oauth2 -> oauth2
                .jwt(jwtConfigurer -> jwtConfigurer
                        .decoder(jwtDecoder())
                        .jwtAuthenticationConverter(jwtAuthenticationConverter())
                )

        );

        return http.build();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
        return jwtAuthenticationConverter;
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        SecretKeySpec keySpec = new SecretKeySpec(signerKey.getBytes(), "HS512");
        return NimbusJwtDecoder
                .withSecretKey(keySpec)
                .macAlgorithm(MacAlgorithm.HS512)
                .build();
    }
}
