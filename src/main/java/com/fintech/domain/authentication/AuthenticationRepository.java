package com.fintech.domain.authentication;

public interface AuthenticationRepository {
    void save(AuthenticationObj authenticationObj);
    boolean isAuthenticationComplete(String identity);
    void setAuthenticationToComplete(String identity);
    boolean checkAuthenticationCode(String identity, String authCode);
}
