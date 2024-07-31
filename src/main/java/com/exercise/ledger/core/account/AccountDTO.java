package com.exercise.ledger.core.account;

import java.util.UUID;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class AccountDTO {

    private UUID accountId;

    private CurrencyType currency;

    private Double balance;
}
