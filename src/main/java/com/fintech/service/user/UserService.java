package com.fintech.service.user;

import com.fintech.domain.authentication.AuthenticationObj;
import com.fintech.domain.authentication.AuthenticationRepository;
import com.fintech.domain.user.User;
import com.fintech.domain.user.UserRepository;
import com.fintech.dto.user.UserDto;
import com.fintech.dto.user.UserRequestDto.UserJoinRequestDto;
import com.fintech.exception.CustomUserApiException;
import com.fintech.service.authentication.AuthenticationMethod;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

import static com.fintech.domain.authentication.AuthenticationState.INPROGRESS;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticationMethod authenticationMethod;
    private final AuthenticationRepository authenticationRepository;

    @Transactional
    public UserDto join(UserJoinRequestDto joinDto) {
        boolean authenticationComplete = authenticationRepository
                .isAuthenticationComplete(joinDto.getEmail());

        if(!authenticationComplete) {
            throw new CustomUserApiException("인증된 사용자가 아닙니다.");
        }

        User findUser = findByEmail(joinDto.getEmail());

        if (findUser != null) {
            throw new CustomUserApiException("중복된 사용자가 있습니다.");
        }

        User joinUser = userRepository.join(joinDto.toEntity(passwordEncoder));

        return UserDto.fromEntity(joinUser);
    }

    public void saveAuthenticationRequestObj(String email) {
        String authCode = authenticationMethod.createNumberStringKey();
        AuthenticationObj authObj = AuthenticationObj.builder()
                .email(email)
                .authCode(authCode)
                .requestedAt(LocalDateTime.now())
                .state(INPROGRESS).build();

        authenticationRepository.save(authObj);
        authenticationMethod.sendMessage(email, authCode);
    }

    public boolean checkAuthenticationCode(String email, String authCode) {
        boolean result = authenticationRepository.checkAuthenticationCode(email, authCode);

        if(result) {
            authenticationRepository.setAuthenticationToComplete(email);
        }

        return result;
    }

    public User findByEmail(String email) {

        return userRepository.findByEmail(email);
    }
}
