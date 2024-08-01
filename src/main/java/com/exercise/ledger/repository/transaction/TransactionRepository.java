package com.exercise.ledger.repository.transaction;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exercise.ledger.core.transaction.Transaction;

import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

}
