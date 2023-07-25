package com.fintech.web.controller;

import com.fintech.domain.user.User;
import com.fintech.dto.ResponseDto;
import com.fintech.dto.user.UserRequestDto.UserJoinRequestDto;
import com.fintech.dto.user.UserResponseDto.UserJoinResponseDto;
import com.fintech.service.UserService;
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

    @PostMapping("/join")
    public ResponseEntity<?> join(
            @RequestBody @Validated UserJoinRequestDto joinDto,
            BindingResult bindingResult
    ) {

        User joinUser = userService.join(joinDto);

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
