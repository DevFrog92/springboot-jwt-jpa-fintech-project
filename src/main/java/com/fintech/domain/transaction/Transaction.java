package com.fintech.domain.transaction;

import com.fintech.domain.account.Account;
import javax.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Table(name = "transaction_tb")
@Entity
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType type;

    @Column(length = 20) // TBD
    private String from;

    @Column(length = 20) // TBD
    private String to;

    @ManyToOne
    private Account widthdrawlAccount;

    @ManyToOne
    private Account depositAccount;

    private Long withdrawalAccountBalance;
    private Long depositAccountBalance;

    @Builder
    public Transaction(
            Long id,
            Long amount,
            String from,
            String to,
            TransactionType type,
            Account widthdrawlAccount,
            Account depositAccount,
            Long withdrawalAccountBalance,
            Long depositAccountBalance
    ) {
        this.id = id;
        this.amount = amount;
        this.from = from;
        this.to = to;
        this.type = type;
        this.widthdrawlAccount = widthdrawlAccount;
        this.depositAccount = depositAccount;
        this.withdrawalAccountBalance = withdrawalAccountBalance;
        this.depositAccountBalance = depositAccountBalance;
    }
}
