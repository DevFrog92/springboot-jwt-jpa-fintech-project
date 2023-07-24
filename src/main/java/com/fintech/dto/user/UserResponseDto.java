package com.fintech.dto.user;

import com.fintech.domain.user.User;
import lombok.Getter;
import lombok.Setter;

public class UserResponseDto {
    @Getter
    @Setter
    public static class UserJoinResponseDto {
        private String username;
        private String email;

        public UserJoinResponseDto(User user) {
            this.username = user.getUsername();
            this.email = user.getEmail();
        }
    }
}
