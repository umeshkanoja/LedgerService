package com.exercise.ledger.repository.account;

import java.util.List;
import java.util.UUID;

import com.exercise.ledger.core.account.Account;
import com.exercise.ledger.core.customer.Customer;

public interface AccountRepoAccessor {

    List<Account> findByCustomer(final Customer customer);

    Account save(final Account account);
}
