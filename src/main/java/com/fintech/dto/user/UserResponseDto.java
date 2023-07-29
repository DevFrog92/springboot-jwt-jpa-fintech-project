package com.fintech.dto.user;

import lombok.Getter;
import lombok.Setter;

public class UserResponseDto {
    @Getter
    @Setter
    public static class UserJoinResponseDto {
        private String username;
        private String email;

        public UserJoinResponseDto(UserDto user) {
            this.username = user.getUsername();
            this.email = user.getEmail();
        }
    }
}
