package com.exercise.ledger.service.account;

import java.util.List;
import java.util.UUID;

import com.exercise.ledger.core.account.Account;
import com.exercise.ledger.core.account.CurrencyType;

public interface AccountService {

    List<Account> getAllAccounts(final UUID customerId);

    Account createAccount(final CurrencyType currency);
}
