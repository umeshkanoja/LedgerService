package com.exercise.ledger.core.transaction;

import java.util.UUID;

import com.exercise.ledger.core.account.CurrencyType;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class TransactionDTO {

    private UUID transactionNumber;

    private UUID customerId;

    private UUID withCustomerId;

    @NotNull(message = "Transaction type is required")
    private TransactionType type;

    private TransactionDirection direction;

    @NotNull(message = "Amount is required")
    @Positive(message = "Amount should be positive")
    private Double amount;

    @NotNull(message = "Currency is required")
    private CurrencyType currency;

    @AssertTrue(message = "WithCustomerId is required when tranaction type is TRANSFER")
    private boolean iswithCustomerId() {
        return type != TransactionType.TRANSFER || withCustomerId != null;
    }
}
