package com.fintech.dto.user;

import com.fintech.domain.user.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

public class UserRequestDto {

    @Getter
    @Setter
    public static class UserJoinRequestDto {
        @NotEmpty
        @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
                message = "이메일 형식에 맞지 않습니다.")
        private String email;

        @NotEmpty
        @Size(min = 4, max = 20, message = "4~20자 이내로 작성해주세요.")
        private String username;

        @NotEmpty
        @Pattern(regexp = "^(?=.*\\d)(?=.[a-zA-Z])[a-zA-Z0-9]{4,20}$",
                message = "영문과 숫자 포함 4~20자 이내로 작성해주세요.")
        private String password;

        public User toEntity(BCryptPasswordEncoder passwordEncoder) {
            return User.builder()
                    .username(username)
                    .email(email)
                    .password(passwordEncoder.encode(password))
                    .createdAt(LocalDateTime.now())
                    .updatedAt(null)
                    .build();
        }
    }
}
