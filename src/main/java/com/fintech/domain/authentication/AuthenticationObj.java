package com.fintech.domain.authentication;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationObj {
    String email;
    String authCode;
    LocalDateTime requestedAt;
    AuthenticationState state;
}
