package com.fintech.domain.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Repository
@Slf4j
@RequiredArgsConstructor
public class UserRepositoryJdbc implements UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public User join(User user) {
        String sql = "insert into " +
                "user_tb (username, email, password, created_at) " +
                "values(?,?,?,?)";

        jdbcTemplate.update(
                sql,
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                user.getCreatedAt());

        return user;
    }

    public User findByEmail(String email) {
        String sql = "select * from user_tb where email = ?";
        try {
            return jdbcTemplate.queryForObject(sql, userRowMapper(), email);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    private RowMapper<User> userRowMapper() {
        return (rs, rowNum) -> User.builder()
                .id(rs.getLong("id"))
                .username(rs.getString("username"))
                .email(rs.getString("email"))
                .password(rs.getString("password"))
                .createdAt(getTimestamp(rs, "created_at"))
                .updatedAt(getTimestamp(rs, "updated_at"))
                .build();
    }

    public void update(String email, String password) {
        String sql = "update user_tb " +
                "set password = ?, updated_at = ? " +
                "where email = ? ";

        Timestamp now = Timestamp.valueOf(LocalDateTime.now());
        jdbcTemplate.update(sql, password, now, email);
    }

    private static LocalDateTime getTimestamp(ResultSet rs, String columnName)
            throws SQLException {

        if (rs.getDate(columnName) == null) {
            return null;
        }

        return new Timestamp(rs.getDate(columnName).getTime()).toLocalDateTime();
    }
}
