package com.exercise.ledger.service.account;

import com.exercise.ledger.core.account.Account;
import com.exercise.ledger.core.account.CurrencyType;

import java.util.List;
import java.util.UUID;
 
public interface AccountService {

    List<Account> getAllAccounts(final UUID customerId);

    List<Account> getCurrencyAccounts(final UUID customerId, final CurrencyType currency);
}
