package com.fintech.web.controller;

import com.fintech.dto.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/account")
public class AccountApiController {

    @GetMapping
    public ResponseEntity<?> getAccount() {
        return new ResponseEntity<>(
                new ResponseDto(
                        HttpStatus.CREATED.value(),
                        "계좌생성에 성공했습니다.",
                        null
                ),
                HttpStatus.CREATED
        );
    }
}
