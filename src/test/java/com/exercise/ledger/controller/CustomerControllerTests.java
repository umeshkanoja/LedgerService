package com.exercise.ledger.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.exercise.ledger.core.account.Account;
import com.exercise.ledger.core.account.AccountDTO;
import com.exercise.ledger.core.account.CurrencyType;
import com.exercise.ledger.core.customer.Customer;
import com.exercise.ledger.core.customer.CustomerDTO;
import com.exercise.ledger.core.transaction.Transaction;
import com.exercise.ledger.core.transaction.TransactionDTO;
import com.exercise.ledger.service.account.AccountService;
import com.exercise.ledger.service.customer.CustomerService;
import com.exercise.ledger.service.transaction.TransactionService;
import com.exercise.ledger.testHelper.TestObjectBuilder;

import java.util.ArrayList;
import java.util.List;

import static com.exercise.ledger.testHelper.TestConstants.VALID_CUSTOMER_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerControllerTests {

    @Mock
    private AccountService mockAccountService;

    @Mock
    private CustomerService mockCustomerService;

    @Mock
    private TransactionService mockTransactionService;

    @InjectMocks
    private CustomerController customerController;

    @Test
    void testGetAllAccountsWithoutCurrency() {
        List<Account> accounts = TestObjectBuilder.buildAccounts();

        when(mockAccountService.getAllAccounts(VALID_CUSTOMER_ID)).thenReturn(accounts);

        List<AccountDTO> accountDTOs = customerController.getAccounts(VALID_CUSTOMER_ID, null);

        assertEquals(accounts.size(), accountDTOs.size());
    }

    @Test
    void testGetAllAccountsWithCurrency() {
        CurrencyType currency = CurrencyType.BITCOIN;
        List<Account> accounts = TestObjectBuilder.buildAccounts()
                .stream()
                .filter(x -> x.getCurrency() == currency)
                .toList();

        when(mockAccountService.getCurrencyAccounts(VALID_CUSTOMER_ID, currency)).thenReturn(accounts);

        List<AccountDTO> accountDTOs = customerController.getAccounts(VALID_CUSTOMER_ID, currency);

        assertEquals(accounts.size(), accountDTOs.size());
    }

    @Test
    void testCreateCustomer() {
        CustomerDTO customerDTO = TestObjectBuilder.buildCustomerDTO();
        Customer customer = TestObjectBuilder.buildCustomer(VALID_CUSTOMER_ID, new ArrayList<>());

        when(mockCustomerService.createCustomer(any())).thenReturn(customer);

        CustomerDTO createdCustomerDTO = customerController.createCustomer(customerDTO);

        assertEquals(VALID_CUSTOMER_ID, createdCustomerDTO.getId());
    }

    @Test
    void testCreateTransaction() {
        TransactionDTO transactionDTO = TestObjectBuilder.buildDepositTransactionDTO();
        Transaction transaction = TestObjectBuilder.buildDepositTransaction(transactionDTO.getAmount(),
                VALID_CUSTOMER_ID);

        when(mockTransactionService.createTransaction(any())).thenReturn(transaction);

        TransactionDTO createdTransactionDTO = customerController.createTransaction(transactionDTO, VALID_CUSTOMER_ID);

        assertEquals(VALID_CUSTOMER_ID, createdTransactionDTO.getCustomerId());
    }
}
