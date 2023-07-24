package com.fintech.domain.account;

import com.fintech.domain.user.User;
import javax.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Table(name = "account_tb")
@Entity
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long balance;

    @Column(nullable = false, unique = true, length = 20)
    private String accountNumber;

    @Column(nullable = false, length = 70)
    private String password;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne
    private User userId;

    @Builder
    public Account(
            Long id,
            Long balance,
            String accountNumber,
            String password,
            LocalDateTime createdAt,
            User userId
    ) {
        this.id = id;
        this.balance = balance;
        this.accountNumber = accountNumber;
        this.password = password;
        this.createdAt = createdAt;
        this.userId = userId;
    }
}
