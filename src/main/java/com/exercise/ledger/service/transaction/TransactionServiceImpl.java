package com.exercise.ledger.service.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.exercise.ledger.core.account.Account;
import com.exercise.ledger.core.customer.Customer;
import com.exercise.ledger.core.transaction.Transaction;
import com.exercise.ledger.core.transaction.TransactionDirection;
import com.exercise.ledger.core.transaction.TransactionType;
import com.exercise.ledger.exception.transaction.InsufficientBalanceException;
import com.exercise.ledger.repository.customer.CustomerRepoAccessor;
import com.exercise.ledger.repository.transaction.TransactionRepoAccessor;

import java.util.UUID;

@Service
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepoAccessor transactionRepoAccessor;

    private final CustomerRepoAccessor customerRepoAccessor;

    @Override
    @Transactional
    public Transaction createTransaction(final Transaction transaction) {
        log.info("Proccessing started for tranaction {}", transaction);
        Customer customer = customerRepoAccessor.findById(transaction.getCustomerId());
        final UUID transactionNumber = UUID.randomUUID();
        log.info("Generated Transaction number is {}", transactionNumber);
        log.info("Updating customer account balance");
        if (transaction.getType() == TransactionType.DEPOSIT) {
            Account account = customer.getAccounts()
                    .stream()
                    .filter(x -> x.getCurrency() == transaction.getCurrency())
                    .findFirst()
                    .get();

            account.setBalance(account.getBalance() + transaction.getAmount());
        } else {
            Account account = customer.getAccounts()
                    .stream()
                    .filter(x -> x.getCurrency() == transaction.getCurrency())
                    .findFirst().get();

            validateTransaction(account, transaction);
            account.setBalance(account.getBalance() - transaction.getAmount());
        }

        transaction.setTransactionNumber(transactionNumber);
        Transaction customerTransaction = transactionRepoAccessor.create(transaction);
        if (transaction.getType() == TransactionType.TRANSFER) {
            Customer receiverCustomer = customerRepoAccessor.findById(transaction.getWithCustomerId());
            Account account = receiverCustomer.getAccounts()
                    .stream()
                    .filter(x -> x.getCurrency() == transaction.getCurrency())
                    .findFirst()
                    .get();

            account.setBalance(account.getBalance() + transaction.getAmount());
            Transaction receiverTransaction = transactionRepoAccessor
                    .create(buildReceiverTransaction(customerTransaction, transactionNumber));
            receiverCustomer.addTransaction(receiverTransaction);
            customerRepoAccessor.create(receiverCustomer);
            log.info("Transaction {} added for receiver customer {}", receiverTransaction, receiverCustomer);
        }

        customer.addTransaction(customerTransaction);
        customerRepoAccessor.create(customer);
        log.info("Transaction {} added for customer {}", customerTransaction, customer);
        return customerTransaction;
    }

    private Transaction buildReceiverTransaction(Transaction transaction, UUID transactionNumber) {
        return Transaction.builder()
                .transactionNumber(transactionNumber)
                .type(transaction.getType())
                .currency(transaction.getCurrency())
                .amount(transaction.getAmount())
                .customerId(transaction.getWithCustomerId())
                .withCustomerId(transaction.getCustomerId())
                .direction(TransactionDirection.CREDIT)
                .build();
    }

    private void validateTransaction(final Account account, final Transaction transaction) {
        if (account.getBalance() < transaction.getAmount()) {
            throw new InsufficientBalanceException("Not enough funds.");
        }
    }
}
