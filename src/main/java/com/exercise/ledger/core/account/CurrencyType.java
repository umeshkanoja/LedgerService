package com.exercise.ledger.core.account;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CurrencyType {
    BITCOIN("Bitcoin"),
    ETHEREUM("Ethereum"),
    MATIC("Matic");

    private String value;
}
