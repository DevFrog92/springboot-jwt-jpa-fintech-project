package com.fintech.domain.user;

public interface UserRepository {
    User join(User user);

    User findByEmail(String email);

    void update(String email, String password);

}
