package com.exercise.ledger.repository.account;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.exercise.ledger.core.account.Account;
import com.exercise.ledger.core.account.CurrencyType;
import com.exercise.ledger.core.customer.Customer;
import com.exercise.ledger.exception.account.AccountsNotFoundException;
import com.exercise.ledger.testHelper.TestObjectBuilder;

import java.util.ArrayList;
import java.util.List;

import static com.exercise.ledger.testHelper.TestConstants.INVALID_CUSTOMER_ID;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class AccountRepoAccessorImplTests {

    @Autowired
    private AccountRepository accountRepository;

    private AccountRepoAccessor accountRepoAccessor;

    @BeforeEach
    void init() {
        accountRepoAccessor = new AccountRepoAccessorImpl(accountRepository);
    }

    @Test
    void testAccountCreation() {

        Account account = accountRepoAccessor.create(CurrencyType.BITCOIN);

        List<Account> accounts = accountRepository.findAll();

        assertTrue(accounts.contains(account));
    }

    @Test
    void testFindByCustomer() {

        List<Account> accounts = accountRepository.findAll();
        Customer customer = accounts.get(0).getCustomer();

        List<Account> expectedAccounts = customer.getAccounts();

        assertIterableEquals(expectedAccounts, accountRepoAccessor.findByCustomer(customer));
    }

    @Test
    void testFindByCustomerThrowsExcepton() {

        Customer customer = TestObjectBuilder.buildCustomer(INVALID_CUSTOMER_ID, new ArrayList<>());

        assertThrows(AccountsNotFoundException.class, () -> accountRepoAccessor.findByCustomer(customer));
    }

    @Test
    void testFindByCustomerAndCurrency() {

        List<Account> accounts = accountRepository.findAll();
        Customer customer = accounts.get(0).getCustomer();
        CurrencyType currency = CurrencyType.BITCOIN;
        List<Account> expectedAccounts = customer.getAccounts()
                .stream()
                .filter(x -> x.getCurrency() == currency)
                .toList();

        assertIterableEquals(expectedAccounts, accountRepoAccessor.findByCustomerAndCurrency(customer, currency));
    }
}
