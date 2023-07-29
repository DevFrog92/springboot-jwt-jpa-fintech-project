package com.fintech.dto.authentication;

import com.fintech.domain.authentication.AuthenticationState;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;

public class AuthenticationRequestDto {
    @Getter
    @Setter
    public static class AuthenticationMailRequestDto {
        @NotEmpty
        @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
                message = "이메일 형식에 맞지 않습니다.")
        String email;
    }

    @Getter
    @Setter
    public static class AuthenticationMailCodeRequestDto {
        @NotEmpty
        @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
                message = "이메일 형식에 맞지 않습니다.")
        String email;

        @NotEmpty
        String authCode;
    }

    @Getter
    @NoArgsConstructor
    public static class AuthenticationDto {
        String email;
        String authCode;
        LocalDateTime requestedAt;
        AuthenticationState state;


    }
}
