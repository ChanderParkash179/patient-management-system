package com.pm.authservice.dtos.response;

import lombok.*;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationResponse {
    private String token;
}