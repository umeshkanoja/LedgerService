package com.exercise.ledger.repository.account;

import java.util.List;

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
    public Account save(final Account account) {
        log.info("Creating account {}", account);
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
}
