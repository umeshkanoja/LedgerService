package com.exercise.ledger.core.account;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Builder
@ToString
public class AccountDTO {

    private UUID accountId;

    private CurrencyType currency;

    private Double balance;
}
