package com.exercise.ledger.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exercise.ledger.core.transaction.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    
}
