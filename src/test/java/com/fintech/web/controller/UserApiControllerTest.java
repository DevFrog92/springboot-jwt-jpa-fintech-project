package com.fintech.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fintech.dto.user.UserRequestDto.UserJoinRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class UserApiControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DisplayName("회원가입 성공")
    void join_success() throws Exception {
        //given
        UserJoinRequestDto userJoinRequestDto = getUserJoinRequestDto(
                "user@gamil.com",
                "user123",
                "test!"
        );

        String requestBody = objectMapper.writeValueAsString(userJoinRequestDto);

        //when
        ResultActions result = mvc.perform(post("/api/join")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody));

        //then
        result.andExpect(status().isCreated());
        result.andExpect(jsonPath("$.status")
                .value(HttpStatus.CREATED.value()));
    }

    @Test
    @DisplayName("회원가입 실패 - Validation Exception")
    void join_fail_validationException() throws Exception {
        //given
        UserJoinRequestDto userJoinRequestDto = getUserJoinRequestDto(
                "usergmail.com",
                "user",
                "tes"
        );

        String requestBody = objectMapper.writeValueAsString(userJoinRequestDto);

        //when
        ResultActions result = mvc.perform(post("/api/join")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody));

        //then
        result
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status")
                        .value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.body.username")
                        .value("영문과 숫자 포함 4~20자 이내로 작성해주세요."))
                .andExpect(jsonPath("$.body.email")
                        .value("이메일 형식에 맞지 않습니다."))
                .andExpect(jsonPath("$.body.password")
                        .value("4~20자 이내로 작성해주세요."));

    }

    private static UserJoinRequestDto getUserJoinRequestDto(String email,
                                                            String username,
                                                            String password) {
        UserJoinRequestDto userJoinRequestDto = new UserJoinRequestDto();
        userJoinRequestDto.setEmail(email);
        userJoinRequestDto.setUsername(username);
        userJoinRequestDto.setPassword(password);
        return userJoinRequestDto;
    }
}
