package com.exercise.ledger.service.account;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.exercise.ledger.core.account.Account;
import com.exercise.ledger.core.account.CurrencyType;
import com.exercise.ledger.core.customer.Customer;
import com.exercise.ledger.repository.account.AccountRepoAccessor;
import com.exercise.ledger.repository.customer.CustomerRepoAccessor;
import com.exercise.ledger.testHelper.TestObjectBuilder;

import java.util.List;

import static com.exercise.ledger.testHelper.TestConstants.VALID_CUSTOMER_ID;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountServiceImplTests {

    @Mock
    private AccountRepoAccessor mockAccountRepoAccessor;

    @Mock
    private CustomerRepoAccessor mockCustomerRepoAccessor;

    @InjectMocks
    private AccountServiceImpl accountServiceImpl;

    @Test
    void testGetAllAccounts() {

        Account account = TestObjectBuilder.buildAccount();
        List<Account> expectedAccounts = List.of(account);
        Customer customer = TestObjectBuilder.buildCustomer(VALID_CUSTOMER_ID, expectedAccounts);

        when(mockCustomerRepoAccessor.findById(VALID_CUSTOMER_ID)).thenReturn(customer);
        when(mockAccountRepoAccessor.findByCustomer(customer)).thenReturn(expectedAccounts);

        List<Account> actualAccounts = accountServiceImpl.getAllAccounts(VALID_CUSTOMER_ID);
        assertIterableEquals(expectedAccounts, actualAccounts);
    }

    @Test
    void testGetCurrencyAccounts() {

        CurrencyType currency = CurrencyType.BITCOIN;
        Account account = TestObjectBuilder.buildAccount(currency);
        List<Account> expectedAccounts = List.of(account);
        Customer customer = TestObjectBuilder.buildCustomer(VALID_CUSTOMER_ID, expectedAccounts);

        when(mockCustomerRepoAccessor.findById(VALID_CUSTOMER_ID)).thenReturn(customer);
        when(mockAccountRepoAccessor.findByCustomerAndCurrency(customer, currency)).thenReturn(expectedAccounts);

        List<Account> actualAccounts = accountServiceImpl.getCurrencyAccounts(VALID_CUSTOMER_ID, currency);
        assertIterableEquals(expectedAccounts, actualAccounts);
    }
}
