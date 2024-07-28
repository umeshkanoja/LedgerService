package com.exercise.ledger.repository.account;

import java.util.List;
import java.util.UUID;

import com.exercise.ledger.core.account.Account;

public interface AccountRepoAccessor {

    List<Account> findByCustomerId(final UUID customerId);

    Account save(final Account account);
}
