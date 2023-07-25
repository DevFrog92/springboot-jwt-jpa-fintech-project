package com.fintech.domain.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@SpringBootTest
@Transactional
class UserRepositoryJdbcTest {

    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("사용자 조회 - 사용자가 존재하지 않는 경우")
    void findByEmail_fail() {
        // given
        String email = "test@gmail.com";
        // when
        User findUser = userRepository.findByEmail(email);
        // then
        assertThat(findUser).isNull();
    }

    @Test
    @DisplayName("사용자 조회 - 사용자가 존재하지 있는 경우")
    void findByEmail_success() {
        //given
        User user = User.builder()
                .email("user@gmail.com")
                .username("user123")
                .password("1234")
                .createdAt(LocalDateTime.now())
                .build();

        User joinUser = userRepository.join(user);

        //when
        User findUser = userRepository.findByEmail(user.getEmail());

        //then
        assertThat(joinUser.getEmail()).isEqualTo(findUser.getEmail());
        assertThat(joinUser.getUsername()).isEqualTo(findUser.getUsername());
        assertThat(joinUser.getPassword()).isEqualTo(findUser.getPassword());
    }

    @Test
    @DisplayName("사용자 회원가입 - 성공")
    void join_success() {
        //given
        User user = User.builder()
                .email("user@gmail.com")
                .username("user123")
                .password("1234")
                .createdAt(LocalDateTime.now())
                .build();

        //when
        User joinUser = userRepository.join(user);

        //then
        assertThat(joinUser.getEmail()).isEqualTo(user.getEmail());
        assertThat(joinUser.getUsername()).isEqualTo(user.getUsername());
        assertThat(joinUser.getPassword()).isEqualTo(user.getPassword());
    }
}
