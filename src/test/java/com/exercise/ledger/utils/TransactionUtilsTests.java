package com.exercise.ledger.utils;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.exercise.ledger.core.transaction.TransactionDirection;
import com.exercise.ledger.core.transaction.TransactionType;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TransactionUtilsTests {

    private static Stream<Arguments> provideTransactionTypeForGetDirections() {
        return Stream.of(
                Arguments.of(TransactionType.DEPOSIT, TransactionDirection.CREDIT),
                Arguments.of(TransactionType.TRANSFER, TransactionDirection.DEBIT),
                Arguments.of(TransactionType.WITHDRAW, TransactionDirection.DEBIT));
    }

    @ParameterizedTest
    @MethodSource("provideTransactionTypeForGetDirections")
    void testGetDirection(TransactionType type, TransactionDirection expectedDirection) {
        TransactionDirection actualDirection = TransactionUtils.getDirection(type);
        assertEquals(expectedDirection, actualDirection);
    }
}
