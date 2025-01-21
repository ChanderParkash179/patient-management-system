package com.pm.authservice.controllers;

import com.pm.authservice.dtos.request.AuthenticationRequest;
import com.pm.authservice.dtos.response.AuthenticationResponse;
import com.pm.authservice.dtos.wrapper.ApiResponse;
import com.pm.authservice.service.auth.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    @Operation(summary = "authenticate user")
    public ResponseEntity<ApiResponse<AuthenticationResponse>> authenticate(@Valid @RequestBody AuthenticationRequest request) {

        AuthenticationResponse response = this.authenticationService.authenticate(request);
        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.success(HttpStatus.OK.value(), "user authenticated successfully!", response));
    }

    @GetMapping("/validate")
    @Operation(summary = "validate token")
    public ResponseEntity<ApiResponse<Boolean>> validate(@RequestHeader("Authorization") String authHeader) {
        Boolean response = this.authenticationService.validate(authHeader);

        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.success(HttpStatus.OK.value(), "token authenticated successfully!", response));
    }
}