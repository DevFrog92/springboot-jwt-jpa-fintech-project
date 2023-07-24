package com.fintech.web.controller;

import com.fintech.dto.ResponseDto;
import com.fintech.dto.user.UserRequestDto.UserJoinRequestDto;
import com.fintech.dto.user.UserResponseDto.UserJoinResponseDto;
import com.fintech.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserApiController {
    private final UserService userService;
    // todo displace to service layer
    private final BCryptPasswordEncoder passwordEncoder;

    @PostMapping("/join")
    public ResponseEntity<?> join(
            @RequestBody @Validated UserJoinRequestDto joinDto,
            BindingResult bindingResult
    ) {

        // todo update by AOP
        if (bindingResult.hasErrors()) {
            // todo update to custom exception
            HashMap<String, String> errorMap = new HashMap<>();

            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                errorMap.put(fieldError.getField(),
                        fieldError.getDefaultMessage());
            }

            return new ResponseEntity<>(
                    new ResponseDto(
                            HttpStatus.BAD_REQUEST.value(),
                            "잘못된 요청입니다.",
                            errorMap
                    ),
                    HttpStatus.BAD_REQUEST
            );
        }

        return new ResponseEntity<>(
                new ResponseDto(
                        HttpStatus.CREATED.value(),
                        "회원가입에 성공했습니다.",
                        new UserJoinResponseDto(
                                joinDto.toEntity(passwordEncoder))
                ),
                HttpStatus.CREATED
        );
    }
}
