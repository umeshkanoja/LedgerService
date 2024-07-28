package com.exercise.ledger.repository.account;

import java.util.List;

import com.exercise.ledger.core.account.Account;

public interface AccountRepoAccessor {
    List<Account> findByCustomerId(final long customerId);
    Account save(final Account account);
}
