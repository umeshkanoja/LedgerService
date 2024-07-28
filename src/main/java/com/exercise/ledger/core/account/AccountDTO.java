package com.exercise.ledger.core.account;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class AccountDTO {
    private Long accountId;
    private CurrencyType currency;
    private Double balance;
}
