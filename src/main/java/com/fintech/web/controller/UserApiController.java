package com.fintech.web.controller;

import com.fintech.dto.ResponseDto;
import com.fintech.dto.authentication.AuthenticationRequestDto;
import com.fintech.dto.authentication.AuthenticationRequestDto.AuthenticationMailCodeRequestDto;
import com.fintech.dto.authentication.AuthenticationRequestDto.AuthenticationMailRequestDto;
import com.fintech.dto.user.UserDto;
import com.fintech.dto.user.UserRequestDto.UserJoinRequestDto;
import com.fintech.dto.user.UserResponseDto.UserJoinResponseDto;
import com.fintech.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserApiController {
    private final UserService userService;

    @PostMapping("/auth-code")
    public ResponseEntity<?> sendAuthMessage(
            @RequestBody @Validated AuthenticationMailRequestDto requestDto,
            BindingResult bindingResult
    ) {
        log.info("requestDto={}", requestDto);

        userService.saveAuthenticationRequestObj(requestDto.getEmail());
        return new ResponseEntity<>(
                new ResponseDto<>(
                        HttpStatus.OK.value(),
                        "이메일 인증을 위한 메일이 발송되었습니다.",
                        null
                ),
                HttpStatus.OK
        );
    }

    @PostMapping("/auth")
    public ResponseEntity<?> checkAuthentication(
            @RequestBody @Validated AuthenticationMailCodeRequestDto requestDto,
            BindingResult bindingResult
    ) {

        HttpStatus statusCode;
        String message;

        boolean state = userService.checkAuthenticationCode(
                requestDto.getEmail(),
                requestDto.getAuthCode());

        if(state) {
            message = "성공적으로 인증 되었습니다.";
            statusCode = HttpStatus.OK;
        }else {
            message = "인증 번호가 잘못 입력되었습니다.";
            statusCode = HttpStatus.BAD_REQUEST;
        }

        return new ResponseEntity<>(
                new ResponseDto<>(
                        statusCode.value(),
                        message,
                        null
                ),
                statusCode
        );
    }

    @PostMapping("/join")
    public ResponseEntity<?> join(
            @RequestBody @Validated UserJoinRequestDto joinDto,
            BindingResult bindingResult
    ) {

        UserDto joinUser = userService.join(joinDto);

        return new ResponseEntity<>(
                new ResponseDto(
                        HttpStatus.CREATED.value(),
                        "회원가입에 성공했습니다.",
                        new UserJoinResponseDto(joinUser)
                ),
                HttpStatus.CREATED
        );
    }
}
