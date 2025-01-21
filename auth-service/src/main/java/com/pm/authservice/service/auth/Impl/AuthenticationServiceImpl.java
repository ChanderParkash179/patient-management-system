package com.pm.authservice.service.auth.Impl;

import com.pm.authservice.dtos.request.AuthenticationRequest;
import com.pm.authservice.dtos.response.AuthenticationResponse;
import com.pm.authservice.entities.User;
import com.pm.authservice.exceptions.TokenMissingException;
import com.pm.authservice.security.jwt.JwtService;
import com.pm.authservice.service.auth.AuthenticationService;
import com.pm.authservice.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final JwtService jwtService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        log.info("authenticating user {}", request);

        log.info("validating user by email or username");
        User user = this.userService.findByEmailOrUsername(request.getUsername());
        log.info("validated successfully user by email or username");

        log.info("validating user by password");
        this.passwordEncoder.matches(request.getPassword(), user.getPassword());
        log.info("validated successfully user by password");

        log.info("generating token");
        String token = this.jwtService.generateToken(user);
        log.info("token generated successfully");

        return AuthenticationResponse.builder()
                .token(token)
                .build();
    }

    @Override
    public Boolean validate(String authHeader) {
        log.info("validating token");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            log.error("invalid or empty token");
            throw new TokenMissingException("invalid or empty token");
        }

        log.info("validating header token");
        return this.jwtService.validateToken(authHeader.substring(7));
    }
}