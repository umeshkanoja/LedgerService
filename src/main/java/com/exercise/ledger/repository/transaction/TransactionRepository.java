package com.exercise.ledger.repository.transaction;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exercise.ledger.core.transaction.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

}
