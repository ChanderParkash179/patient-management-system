package com.pm.authservice.service.auth;

import com.pm.authservice.dtos.request.AuthenticationRequest;
import com.pm.authservice.dtos.response.AuthenticationResponse;

public interface AuthenticationService {

    AuthenticationResponse authenticate(AuthenticationRequest request);

    Boolean validate(String authHeader);
}