package com.fintech.domain.authentication;

public enum AuthenticationState {
    INPROGRESS("인증중"),
    COMPLETE("인증완료");

    private String value;

    AuthenticationState(String value) {
        this.value = value;
    }
}
