package com.exercise.ledger.repository.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.exercise.ledger.core.transaction.Transaction;

@Component
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class TransactionRepoAccessorImpl implements TransactionRepoAccessor {

    private final TransactionRepository transactionRepository;

    @Override
    public Transaction create(Transaction transaction) {
        log.info("Creating transaction {}", transaction);
        Transaction createdTransaction = transactionRepository.save(transaction);

        log.info("Transaction record after creation {}", createdTransaction);
        return createdTransaction;
    }
}
