package com.fintech.domain.authentication;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.fintech.domain.authentication.AuthenticationState.COMPLETE;

@Repository
@Slf4j
public class AuthenticationRepositoryMemory implements AuthenticationRepository {
    private static final Map<String, AuthenticationObj> store =
            new ConcurrentHashMap<>();

    @Override
    public void save(AuthenticationObj authObj) {
        store.put(authObj.email, authObj);
    }

    @Override
    public boolean isAuthenticationComplete(String identity) {
        if(store.get(identity) == null) {
            return false;
        }

        return store.get(identity)
                .getState()
                .equals(COMPLETE);
    }

    @Override
    public void setAuthenticationToComplete(String identity) {
        AuthenticationObj authenticationObj = store.get(identity);
        authenticationObj.setState(COMPLETE);
    }

    @Override
    public boolean checkAuthenticationCode(String identity,
                                           String authCode) {
        if(store.get(identity) == null) {
            return false;
        }

        return store.get(identity).getAuthCode().equals(authCode);
    }
}
