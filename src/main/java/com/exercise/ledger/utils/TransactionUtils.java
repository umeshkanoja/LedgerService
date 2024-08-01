package com.exercise.ledger.utils;

import lombok.experimental.UtilityClass;

import com.exercise.ledger.core.transaction.TransactionDirection;
import com.exercise.ledger.core.transaction.TransactionType;

@UtilityClass
public class TransactionUtils {

    public static TransactionDirection getDirection(final TransactionType type) {
        return switch (type) {
            case DEPOSIT -> TransactionDirection.CREDIT;
            default -> TransactionDirection.DEBIT;
        };
    }
}
