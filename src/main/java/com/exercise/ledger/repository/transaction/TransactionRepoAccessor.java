package com.exercise.ledger.repository.transaction;

import com.exercise.ledger.core.transaction.Transaction;

public interface TransactionRepoAccessor {

    Transaction create(final Transaction transaction);
}
