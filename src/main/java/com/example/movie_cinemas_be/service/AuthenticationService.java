package com.example.movie_cinemas_be.service;

import com.example.movie_cinemas_be.dtos.request.AuthenticationRequest;
import com.example.movie_cinemas_be.dtos.request.IntrospectRequest;
import com.example.movie_cinemas_be.dtos.response.AuthenticationResponse;
import com.example.movie_cinemas_be.dtos.response.IntrospectResponse;
import com.example.movie_cinemas_be.entitys.User;
import com.example.movie_cinemas_be.exception.CustomException;
import com.example.movie_cinemas_be.exception.ErrorCode;
import com.example.movie_cinemas_be.reponsitory.UserRepository;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.dialect.function.CoalesceIfnullEmulation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;

@Service
@Slf4j
public class AuthenticationService {

    private final UserRepository userRepository;

    @NonFinal
    @Value("${jwt.signerKey}")
    protected String SIGNER_KEY;

    public AuthenticationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        var user = userRepository.findByUsername(authenticationRequest.getUsername()).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_EXISTS_EXCEPTION));

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

        boolean authenticated =  passwordEncoder.matches(authenticationRequest.getPassword(), user.getPassword());

        if(!authenticated){
            throw new CustomException(ErrorCode.UNAUTHENTICATED_EXCEPTION);
        }

        var token  = geneerateToken(user);

        return AuthenticationResponse.builder()
                .token(token)
                .authenticated(true)
                .build();
    }

    private String geneerateToken(User user){

        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .issuer("xuanduong.com")
                .issueTime(new Date())
                .expirationTime(new Date(Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()))
                .claim("scope", buildScope(user))
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(header, payload);

        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Cannot create token : " ,e.getMessage());
            throw new RuntimeException(e);
        }

    }

    private String  buildScope(User user){
        StringJoiner scopes = new StringJoiner(" ");
//        if(!CollectionUtils.isEmpty(user.getRoles())){
//            user.getRoles().forEach(role -> scopes.add(role));
//        }
        return scopes.toString();
    }

    public IntrospectResponse introspect(IntrospectRequest introspectRequest) {
        var token = introspectRequest.getToken();

        try {
            JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());

            SignedJWT signedJWT = SignedJWT.parse(token);

            Date expirationDate = signedJWT.getJWTClaimsSet().getExpirationTime();

            boolean verified = signedJWT.verify(verifier);

            return IntrospectResponse.builder()
                    .valid(verified && expirationDate.after(new Date()))
                    .build();
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return null;
    }
}
