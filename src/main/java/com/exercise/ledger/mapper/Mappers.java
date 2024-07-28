package com.exercise.ledger.mapper;

import java.util.ArrayList;

import com.exercise.ledger.core.account.Account;
import com.exercise.ledger.core.account.AccountDTO;
import com.exercise.ledger.core.customer.Customer;
import com.exercise.ledger.core.customer.CustomerDTO;
import com.exercise.ledger.core.transaction.Transaction;
import com.exercise.ledger.core.transaction.TransactionDTO;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Mappers {
    public static AccountDTO mapToAccountDTO(final Account account) {
        return AccountDTO.builder()
                .currency(account.getCurrency())
                .balance(account.getBalance())
                .accountId(account.getId())
                .build();
    }

    public static Customer mapToCustomer(final CustomerDTO customerDTO) {
        return Customer.builder()
                .userName(customerDTO.getUserName())
                .email(customerDTO.getEmail())
                .id(customerDTO.getId())
                .accounts(new ArrayList<>())
                .build();
    }

    public static CustomerDTO mapToCustomerDTO(final Customer customer) {
        return CustomerDTO.builder()
                .id(customer.getId())
                .userName(customer.getUserName())
                .email(customer.getEmail())
                .accounts(customer.getAccounts().stream().map(Mappers::mapToAccountDTO).toList())
                .build();
    }

    public static Transaction mapToTransaction(final TransactionDTO transactionDTO) {
        return Transaction.builder()
                .id(transactionDTO.getId())
                .customerId(transactionDTO.getCustomerId())
                .withCustomerId(transactionDTO.getWithCustomerId())
                .type(transactionDTO.getType())
                .direction(transactionDTO.getDirection())
                .amount(transactionDTO.getAmount())
                .build();
    }

    public static TransactionDTO mapToTransactionDTO(final Transaction transaction) {
        return TransactionDTO.builder()
                .id(transaction.getId())
                .withCustomerId(transaction.getWithCustomerId())
                .type(transaction.getType())
                .direction(transaction.getDirection())
                .amount(transaction.getAmount())
                .build();
    }
}
