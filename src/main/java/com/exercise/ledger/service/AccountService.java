package com.exercise.ledger.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exercise.ledger.core.account.Account;
import com.exercise.ledger.repository.account.AccountRepoAccessor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AccountService {
    private final AccountRepoAccessor accountRepoAccessor;

    public List<Account> getAllAccounts(final long customerId) {
        List<Account> accounts = accountRepoAccessor.findByCustomerId(customerId);
        log.info("Accounts of user {}: \n {}", customerId, accounts);
        return accounts;
    }

    public Account addAccount(final Account account) {
        Account createdAccount = accountRepoAccessor.save(account);
        log.info("New account created {}", createdAccount);
        return createdAccount;
    }
}
