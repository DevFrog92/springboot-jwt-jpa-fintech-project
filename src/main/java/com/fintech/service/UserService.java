package com.fintech.service;

import com.fintech.domain.user.User;
import com.fintech.domain.user.UserRepository;
import com.fintech.dto.user.UserDto;
import com.fintech.dto.user.UserRequestDto.UserJoinRequestDto;
import com.fintech.exception.CustomUserApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public UserDto join(UserJoinRequestDto joinDto) {
        User findUser = findByEmail(joinDto.getEmail());

        if (findUser != null) {
            throw new CustomUserApiException("중복된 사용자가 있습니다.");
        }

        User joinUser = userRepository.join(joinDto.toEntity(passwordEncoder));
        return UserDto.fromEntity(joinUser);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
