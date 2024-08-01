package com.exercise.ledger.repository.account;

import com.exercise.ledger.core.account.Account;
import com.exercise.ledger.core.account.CurrencyType;
import com.exercise.ledger.core.customer.Customer;

import java.util.List;

public interface AccountRepoAccessor {

    List<Account> findByCustomer(final Customer customer);

    List<Account> findByCustomerAndCurrency(final Customer customer, final CurrencyType currency);

    Account create(final CurrencyType currency);
}
