package com.exercise.ledger.repository.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.exercise.ledger.core.account.Account;
import com.exercise.ledger.core.account.CurrencyType;
import com.exercise.ledger.core.customer.Customer;
import com.exercise.ledger.exception.account.AccountsNotFoundException;

import java.util.List;

@Component
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class AccountRepoAccessorImpl implements AccountRepoAccessor {

    private static final Double INITIAL_ACCOUNT_BALANCE = 0.0;

    private final AccountRepository accountRepository;

    @Override
    public Account create(final CurrencyType currency) {
        log.info("Creating account for currency {} with account balance {}", currency, INITIAL_ACCOUNT_BALANCE);
        Account account = Account.builder()
                .balance(INITIAL_ACCOUNT_BALANCE)
                .currency(currency)
                .build();

        Account createdAccount = accountRepository.save(account);
        log.info("Account record after creation: {}", createdAccount);
        return createdAccount;
    }

    @Override
    public List<Account> findByCustomer(final Customer customer) {
        log.info("Accessing accounts of customer {}", customer);
        List<Account> accounts = accountRepository.findByCustomer(customer);
        if (accounts.isEmpty()) {
            throw new AccountsNotFoundException(String.format("No accounts found for customer %s", customer.getId()));
        }

        log.info("Records of customerId {}: {}", customer.getId(), accounts);
        return accounts;
    }

    @Override
    public List<Account> findByCustomerAndCurrency(Customer customer, CurrencyType currency) {
        log.info("Accessing accounts of customer {} for currency {}", customer, currency);
        List<Account> accounts = accountRepository.findByCustomerAndCurrency(customer, currency);
        if (accounts.isEmpty()) {
            throw new AccountsNotFoundException(
                    String.format("No currency account found for customer %s", customer.getId()));
        }

        log.info("Records of customerId {}: {}", customer.getId(), accounts);
        return accounts;
    }
}
