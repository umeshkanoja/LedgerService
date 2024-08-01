package com.exercise.ledger.repository.transaction;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.exercise.ledger.core.transaction.Transaction;
import com.exercise.ledger.testHelper.TestObjectBuilder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class TransactionRepoAccessorImplTests {
    @Autowired
    private TransactionRepository transactionRepository;

    private TransactionRepoAccessor transactionRepoAccessor;

    @BeforeEach
    void init() {
        transactionRepoAccessor = new TransactionRepoAccessorImpl(transactionRepository);
    }

    @Test
    void testTransactionCreation() {
        Transaction transaction = transactionRepoAccessor.create(TestObjectBuilder.buildTransaction());

        List<Transaction> transactions = transactionRepository.findAll();

        assertTrue(transactions.contains(transaction));
    }
}
