package com.exercise.ledger.service.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exercise.ledger.core.account.Account;
import com.exercise.ledger.core.customer.Customer;
import com.exercise.ledger.core.transaction.Transaction;
import com.exercise.ledger.core.transaction.TransactionDirection;
import com.exercise.ledger.core.transaction.TransactionType;
import com.exercise.ledger.repository.customer.CustomerRepoAccessor;
import com.exercise.ledger.repository.transaction.TransactionRepoAccessor;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepoAccessor transactionRepoAccessor;

    private final CustomerRepoAccessor customerRepoAccessor;

    @Override
    @Transactional
    public Transaction createTransaction(final Transaction transaction) {
        Transaction savedTransaction = transactionRepoAccessor.save(transaction);
        Customer customer = customerRepoAccessor.findById(transaction.getCustomerId());
        customer.addTransaction(savedTransaction);
        if (transaction.getType() == TransactionType.TRANSFER) {
            Transaction receiverTransaction = transactionRepoAccessor
                    .save(buildReceiverTransaction(transaction));
            Customer receiverCustomer = customerRepoAccessor.findById(receiverTransaction.getCustomerId());
            receiverCustomer.addTransaction(receiverTransaction);
            Account account = receiverCustomer.getAccounts().stream()
                    .filter(x -> x.getCurrency() == transaction.getCurrency())
                    .findFirst().get();

            account.setBalance(account.getBalance() + transaction.getAmount());
            customerRepoAccessor.save(receiverCustomer);
        }

        if (transaction.getType() == TransactionType.DEPOSIT) {
            Account account = customer.getAccounts().stream().filter(x -> x.getCurrency() == transaction.getCurrency())
                    .findFirst().get();

            account.setBalance(account.getBalance() + transaction.getAmount());
        } else {
            Account account = customer.getAccounts().stream().filter(x -> x.getCurrency() == transaction.getCurrency())
                    .findFirst().get();

            account.setBalance(account.getBalance() - transaction.getAmount());
        }

        customerRepoAccessor.save(customer);
        return savedTransaction;
    }

    private Transaction buildReceiverTransaction(Transaction transaction) {
        return Transaction.builder()
                .amount(transaction.getAmount())
                .customerId(transaction.getWithCustomerId())
                .withCustomerId(transaction.getCustomerId())
                .direction(TransactionDirection.CREDIT)
                .build();
    }
}
