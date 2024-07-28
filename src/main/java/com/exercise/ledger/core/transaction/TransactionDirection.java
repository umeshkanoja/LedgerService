package com.exercise.ledger.core.transaction;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TransactionDirection {
    CREDIT("Credit"),
    DEBIT("Debit");

    private String value;
}
