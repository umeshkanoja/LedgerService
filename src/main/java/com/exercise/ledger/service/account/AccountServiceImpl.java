package com.exercise.ledger.service.account;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exercise.ledger.core.account.Account;
import com.exercise.ledger.core.account.CurrencyType;
import com.exercise.ledger.repository.account.AccountRepoAccessor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AccountServiceImpl implements AccountService {

    private final AccountRepoAccessor accountRepoAccessor;

    @Override
    public List<Account> getAllAccounts(final UUID customerId) {
        List<Account> accounts = accountRepoAccessor.findByCustomerId(customerId);
        log.info("Accounts of user {}: \n {}", customerId, accounts);
        return accounts;
    }

    @Override
    public Account createAccount(final CurrencyType currency) {
        Account account = Account.builder()
                .balance(0.0)
                .currency(currency)
                .build();

        Account createdAccount = accountRepoAccessor.save(account);
        log.info("New account created {}", createdAccount);
        return createdAccount;
    }
}
