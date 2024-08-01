package com.exercise.ledger.service.transaction;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.exercise.ledger.core.account.Account;
import com.exercise.ledger.core.customer.Customer;
import com.exercise.ledger.core.transaction.Transaction;
import com.exercise.ledger.exception.transaction.InsufficientBalanceException;
import com.exercise.ledger.repository.customer.CustomerRepoAccessor;
import com.exercise.ledger.repository.transaction.TransactionRepoAccessor;
import com.exercise.ledger.testHelper.TestObjectBuilder;

import java.util.List;
import java.util.UUID;

import static com.exercise.ledger.testHelper.TestConstants.VALID_CUSTOMER_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransactionServiceImplTests {

    @Mock
    private TransactionRepoAccessor mockTransactionRepoAccessor;

    @Mock
    private CustomerRepoAccessor mockCustomerRepoAccessor;

    @InjectMocks
    private TransactionServiceImpl transactionServiceImpl;

    @Test
    void testDepositTransaction() {
        Double amount = 100.0;
        Account account = TestObjectBuilder.buildAccount();
        List<Account> accounts = List.of(account);
        Customer customer = TestObjectBuilder.buildCustomer(VALID_CUSTOMER_ID, accounts);
        Transaction deposit = TestObjectBuilder.buildDepositTransaction(amount, VALID_CUSTOMER_ID);

        when(mockCustomerRepoAccessor.findById(VALID_CUSTOMER_ID)).thenReturn(customer);
        when(mockCustomerRepoAccessor.create(any())).thenReturn(customer);
        when(mockTransactionRepoAccessor.create(any())).thenReturn(deposit);

        transactionServiceImpl.createTransaction(deposit);

        assertEquals(amount, customer.getAccounts().get(0).getBalance());
        assertEquals(1, customer.getTransactions().size());
    }

    @Test
    void testWithdrawTransaction() {
        Double amount = 100.0;
        Double accountBalance = 200.0;
        Account account = TestObjectBuilder.buildAccount();
        account.setBalance(accountBalance);
        List<Account> accounts = List.of(account);
        Customer customer = TestObjectBuilder.buildCustomer(VALID_CUSTOMER_ID, accounts);
        Transaction withdraw = TestObjectBuilder.buildWithdrawTransaction(amount, VALID_CUSTOMER_ID);

        when(mockCustomerRepoAccessor.findById(VALID_CUSTOMER_ID)).thenReturn(customer);
        when(mockCustomerRepoAccessor.create(any())).thenReturn(customer);
        when(mockTransactionRepoAccessor.create(any())).thenReturn(withdraw);

        transactionServiceImpl.createTransaction(withdraw);

        Double expectedBalance = accountBalance - amount;
        assertEquals(expectedBalance, customer.getAccounts().get(0).getBalance());
        assertEquals(1, customer.getTransactions().size());
    }

    @Test
    void testWithdrawTransactionThrowsException() {
        Double amount = 1000.0;
        Double accountBalance = 200.0;
        Account account = TestObjectBuilder.buildAccount();
        account.setBalance(accountBalance);
        List<Account> accounts = List.of(account);
        Customer customer = TestObjectBuilder.buildCustomer(VALID_CUSTOMER_ID, accounts);
        Transaction withdraw = TestObjectBuilder.buildWithdrawTransaction(amount, VALID_CUSTOMER_ID);

        when(mockCustomerRepoAccessor.findById(VALID_CUSTOMER_ID)).thenReturn(customer);

        assertThrows(InsufficientBalanceException.class, () -> transactionServiceImpl.createTransaction(withdraw));
    }

    @Test
    void testTransferTransaction() {
        Double amount = 100.0;
        Double accountBalance = 200.0;

        Account firstCustomerAccount = TestObjectBuilder.buildAccount();
        firstCustomerAccount.setBalance(accountBalance);
        Customer firstCustomer = TestObjectBuilder.buildCustomer(VALID_CUSTOMER_ID, List.of(firstCustomerAccount));

        Account secondCustomerAccount = TestObjectBuilder.buildAccount();
        secondCustomerAccount.setBalance(accountBalance);
        final UUID secondCustomerId = UUID.randomUUID();
        Customer secondCustomer = TestObjectBuilder.buildCustomer(secondCustomerId, List.of(secondCustomerAccount));

        Transaction transfer = TestObjectBuilder.buildTransferTransaction(amount, VALID_CUSTOMER_ID, secondCustomerId);

        when(mockCustomerRepoAccessor.findById(VALID_CUSTOMER_ID)).thenReturn(firstCustomer);
        when(mockCustomerRepoAccessor.findById(secondCustomerId)).thenReturn(secondCustomer);
        when(mockCustomerRepoAccessor.create(any(Customer.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(mockTransactionRepoAccessor.create(any(Transaction.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        transactionServiceImpl.createTransaction(transfer);

        Double expectedFirstCustomerBalance = accountBalance - amount;
        Double expectedSecondCustomerBalance = accountBalance + amount;
        assertEquals(expectedFirstCustomerBalance, firstCustomer.getAccounts().get(0).getBalance());
        assertEquals(1, firstCustomer.getTransactions().size());
        assertEquals(expectedSecondCustomerBalance, secondCustomer.getAccounts().get(0).getBalance());
        assertEquals(1, secondCustomer.getTransactions().size());
        assertEquals(firstCustomer.getTransactions().get(0).getTransactionNumber(),
                secondCustomer.getTransactions().get(0).getTransactionNumber());
    }

    @Test
    void testTransferTransactionThrowsException() {
        Double amount = 400.0;
        Double accountBalance = 200.0;

        Account firstCustomerAccount = TestObjectBuilder.buildAccount();
        firstCustomerAccount.setBalance(accountBalance);
        Customer firstCustomer = TestObjectBuilder.buildCustomer(VALID_CUSTOMER_ID, List.of(firstCustomerAccount));

        Account secondCustomerAccount = TestObjectBuilder.buildAccount();
        secondCustomerAccount.setBalance(accountBalance);
        final UUID secondCustomerId = UUID.randomUUID();

        Transaction transfer = TestObjectBuilder.buildTransferTransaction(amount, VALID_CUSTOMER_ID, secondCustomerId);

        when(mockCustomerRepoAccessor.findById(VALID_CUSTOMER_ID)).thenReturn(firstCustomer);

        assertThrows(InsufficientBalanceException.class, () -> transactionServiceImpl.createTransaction(transfer));
    }
}
