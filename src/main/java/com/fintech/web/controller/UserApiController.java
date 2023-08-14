package com.fintech.web.controller;

import com.fintech.dto.ResponseDto;
import com.fintech.domain.user.dto.authentication.AuthenticationRequestDto.AuthenticationMailCodeRequestDto;
import com.fintech.domain.user.dto.authentication.AuthenticationRequestDto.AuthenticationMailRequestDto;
import com.fintech.domain.user.dto.user.UserDto;
import com.fintech.domain.user.dto.user.UserRequestDto.UserJoinRequestDto;
import com.fintech.domain.user.dto.user.UserRequestDto.UserLoginRequestDto;
import com.fintech.domain.user.dto.user.UserResponseDto.UserJoinResponseDto;
import com.fintech.domain.user.service.user.UserService;
import com.fintech.web.session.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@RestController
@RequestMapping("/api/user")
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

        if (state) {
            message = "성공적으로 인증 되었습니다.";
            statusCode = HttpStatus.OK;
        } else {
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

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody @Validated UserLoginRequestDto loginDto,
            BindingResult bindingResult,
            HttpServletRequest request
    ) {

        HttpSession session = request.getSession();
        String attribute = (String) session.getAttribute(SessionConst.LOGIN_USER);
        if(StringUtils.hasText(attribute)) {
            throw new IllegalArgumentException("로그인된 유저입니다.");
        }

        UserDto findUser = userService.findByEmail(loginDto.getEmail());

        if(findUser == null) {
            throw new IllegalArgumentException("가입되지 않은 사용자 입니다.");
        }

        UserDto loginUser = userService.login(loginDto);

        if(loginUser == null) {
            throw new IllegalArgumentException("아이디 또는 비밀번호가 틀렸습니다.");
        }

        session.setAttribute(SessionConst.LOGIN_USER, loginUser.getEmail());

        return new ResponseEntity<>(
                new ResponseDto(
                        HttpStatus.OK.value(),
                        "로그인 되었습니다.",
                        loginUser
                ),
                HttpStatus.OK
        );
    }
}
