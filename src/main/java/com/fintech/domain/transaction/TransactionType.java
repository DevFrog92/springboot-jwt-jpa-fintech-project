package com.fintech.domain.transaction;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TransactionType {
    DEFAULT("입출금"),
    WITHDRAW("출금"),
    DEPOSIT("입금"),
    TRANSFER("송금");

    private String value;
}
