package com.exercise.ledger.core.transaction;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class TransactionDTO {
    private Long id;
    private Long customerId;
    private Long withCustomerId;
    private TransactionType type;
    private TransactionDirection direction;
    private Double amount;
}
