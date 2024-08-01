package com.exercise.ledger.testHelper;

import lombok.experimental.UtilityClass;

import com.exercise.ledger.core.account.Account;
import com.exercise.ledger.core.account.CurrencyType;
import com.exercise.ledger.core.customer.Customer;
import com.exercise.ledger.core.customer.CustomerDTO;
import com.exercise.ledger.core.transaction.Transaction;
import com.exercise.ledger.core.transaction.TransactionDTO;
import com.exercise.ledger.core.transaction.TransactionDirection;
import com.exercise.ledger.core.transaction.TransactionType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static com.exercise.ledger.testHelper.TestConstants.TEST_EMAIL;
import static com.exercise.ledger.testHelper.TestConstants.TEST_USER_NAME;
import static com.exercise.ledger.testHelper.TestConstants.TRANSACTION_NUMBER;
import static com.exercise.ledger.testHelper.TestConstants.VALID_CUSTOMER_ID;

@UtilityClass
public class TestObjectBuilder {
    public static Account buildAccount() {
        return buildAccount(CurrencyType.BITCOIN);
    }

    public static Account buildAccount(CurrencyType currencyType) {
        return Account.builder()
                .balance(0.0)
                .currency(currencyType)
                .build();
    }

    public static List<Account> buildAccounts() {
        return Arrays.stream(CurrencyType.values())
                .map(TestObjectBuilder::buildAccount)
                .toList();
    }

    public static Customer buildCustomer() {
        return Customer.builder()
                .userName(TEST_USER_NAME)
                .email(TEST_EMAIL)
                .accounts(new ArrayList<>())
                .transactions(new ArrayList<>())
                .build();
    }

    public static Customer buildCustomer(final UUID customerId, final List<Account> accounts) {
        return Customer.builder()
                .id(customerId)
                .userName(TEST_USER_NAME)
                .email(TEST_EMAIL)
                .accounts(accounts)
                .transactions(new ArrayList<>())
                .build();
    }

    public static CustomerDTO buildCustomerDTO() {
        return CustomerDTO.builder()
                .userName(TEST_USER_NAME)
                .email(TEST_EMAIL)
                .build();
    }

    public static Transaction buildTransaction() {
        return Transaction.builder()
                .transactionNumber(TRANSACTION_NUMBER)
                .type(TransactionType.DEPOSIT)
                .currency(CurrencyType.BITCOIN)
                .amount(100.0)
                .customerId(VALID_CUSTOMER_ID)
                .direction(TransactionDirection.CREDIT)
                .build();
    }

    public static Transaction buildDepositTransaction(final Double amount, final UUID customerId) {
        return Transaction.builder()
                .type(TransactionType.DEPOSIT)
                .currency(CurrencyType.BITCOIN)
                .amount(amount)
                .customerId(customerId)
                .direction(TransactionDirection.CREDIT)
                .build();
    }

    public static TransactionDTO buildDepositTransactionDTO() {
        return TransactionDTO.builder()
                .type(TransactionType.DEPOSIT)
                .currency(CurrencyType.BITCOIN)
                .amount(100.0)
                .build();
    }

    public static Transaction buildWithdrawTransaction(final Double amount, final UUID customerId) {
        return Transaction.builder()
                .type(TransactionType.WITHDRAW)
                .currency(CurrencyType.BITCOIN)
                .amount(amount)
                .customerId(customerId)
                .direction(TransactionDirection.DEBIT)
                .build();
    }

    public static Transaction buildTransferTransaction(final Double amount, final UUID firstCustomer,
            final UUID secondCustomer) {
        return Transaction.builder()
                .type(TransactionType.TRANSFER)
                .currency(CurrencyType.BITCOIN)
                .amount(amount)
                .customerId(firstCustomer)
                .withCustomerId(secondCustomer)
                .direction(TransactionDirection.DEBIT)
                .build();
    }
}
