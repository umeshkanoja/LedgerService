package com.exercise.ledger.utils;

import com.exercise.ledger.core.transaction.TransactionDirection;
import com.exercise.ledger.core.transaction.TransactionType;

import lombok.experimental.UtilityClass;

@UtilityClass
public class TransactionUtils {

    public static TransactionDirection getDirection(final TransactionType type) {
        return switch (type) {
            case DEPOSIT -> TransactionDirection.CREDIT;
            default -> TransactionDirection.DEBIT;
        };
    }
}
