package com.exercise.ledger.repository.account;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.exercise.ledger.core.account.Account;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class AccountRepoAccessorImpl implements AccountRepoAccessor {

    private final AccountRepository accountRepository;

    @Override
    public List<Account> findByCustomerId(final long customerId) {
        return accountRepository.findByCustomerId(customerId);
    }

    @Override
    public Account save(Account account) {
        return accountRepository.save(account);
    }
}
