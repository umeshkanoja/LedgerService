package com.exercise.ledger.repository.account;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.exercise.ledger.core.account.Account;
import com.exercise.ledger.core.customer.Customer;
import com.exercise.ledger.exception.account.AccountsNotFoundException;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class AccountRepoAccessorImpl implements AccountRepoAccessor {

    private final AccountRepository accountRepository;

    @Override
    public Account save(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public List<Account> findByCustomer(Customer customer) {
        List<Account> accounts = accountRepository.findByCustomer(customer);
        if (accounts.isEmpty()) {
            throw new AccountsNotFoundException(String.format("No accounts found for customer %s", customer.getId()));
        }

        return accounts;
    }
}
