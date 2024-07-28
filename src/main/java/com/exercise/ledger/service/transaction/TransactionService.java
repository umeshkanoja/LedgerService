package com.exercise.ledger.service.transaction;

import com.exercise.ledger.core.transaction.Transaction;

public interface TransactionService {

    Transaction createTransaction(final Transaction transaction);
}
