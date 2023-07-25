package com.fintech.domain.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;
import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;

@Repository
@Slf4j
public class UserRepositoryJdbc {

    private final DataSource dataSource;
    private final SQLExceptionTranslator sqlExceptionTranslator;

    public UserRepositoryJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
        this.sqlExceptionTranslator = new SQLErrorCodeSQLExceptionTranslator(dataSource);
    }

    public User join(User user) {
        String sql = "insert into " +
                "user_tb (username, email, password, created_at) " +
                "values(?,?,?,?)";
        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = getConnection();
            log.info("connection = {}, class = {}", con, con.getClass());
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getEmail());
            pstmt.setString(3, user.getPassword());
            pstmt.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
            pstmt.executeUpdate();

            return user;
        } catch (SQLException e) {
            throw sqlExceptionTranslator.translate("save", sql, e);
        } finally {
            close(con, pstmt, null);
        }
    }

    public User findByEmail(String email) {
        String sql = "select * from user_tb where email = ?";
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, email);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                LocalDateTime createdAt = getTimestamp(rs, "created_At");
                LocalDateTime updatedAt = getTimestamp(rs, "updated_At");

                return User.builder()
                        .id(rs.getLong("id"))
                        .username(rs.getString("username"))
                        .email(rs.getString("email"))
                        .password(rs.getString("password"))
                        .createdAt(createdAt)
                        .updatedAt(updatedAt)
                        .build();
            }

            return null;
        } catch (SQLException e) {
            throw sqlExceptionTranslator.translate("findByEmail", sql, e);
        } finally {
            close(con, pstmt, rs);
        }
    }

    public void update(String email, String password) {
        String sql = "update user_tb " +
                "set password = ?, updated_at = ? " +
                "where email = ? ";

        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = getConnection();
            log.info("connection = {}, class = {}", con, con.getClass());
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, email);
            pstmt.setString(2, password);
            pstmt.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw sqlExceptionTranslator.translate("update", sql, e);
        } finally {
            close(con, pstmt, null);
        }
    }

    private static LocalDateTime getTimestamp(ResultSet rs, String columnName)
            throws SQLException {

        if (rs.getDate(columnName) == null) {
            return null;
        }

        return new Timestamp(rs.getDate(columnName).getTime()).toLocalDateTime();
    }

    private void close(Connection con, Statement stmt, ResultSet rs) {
        JdbcUtils.closeResultSet(rs);
        JdbcUtils.closeStatement(stmt);
//        JdbcUtils.closeConnection(con);
        DataSourceUtils.releaseConnection(con, dataSource);
    }

    private Connection getConnection() throws SQLException {
//         JdbcConnection -> not use conection pool
//                Connection con = DriverManager.getConnection("jdbc:h2:mem:testdb;MODE=MySQL", "SA", "");
//                return con;

        log.info("datasource={}, class={}", dataSource, dataSource.getClass());
//        return dataSource.getConnection();

        // Transactional 동기화를 사용하기 위해서는 DataSourceUtils.getConnection 을 사용해야 한다. -> 왜 사용해야 하는가?

        return DataSourceUtils.getConnection(dataSource);
    }

}
