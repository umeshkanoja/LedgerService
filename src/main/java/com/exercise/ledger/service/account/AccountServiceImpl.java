package com.exercise.ledger.service.account;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exercise.ledger.core.account.Account;
import com.exercise.ledger.core.account.CurrencyType;
import com.exercise.ledger.core.customer.Customer;
import com.exercise.ledger.repository.account.AccountRepoAccessor;
import com.exercise.ledger.repository.customer.CustomerRepoAccessor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AccountServiceImpl implements AccountService {

    private static final Double INITIAL_ACCOUNT_BALANCE = 0.0;

    private final AccountRepoAccessor accountRepoAccessor;

    private final CustomerRepoAccessor customerRepoAccessor;

    @Override
    public List<Account> getAllAccounts(final UUID customerId) {
        log.info("Getting all accounts of customer {}", customerId);
        Customer customer = customerRepoAccessor.findById(customerId);

        return accountRepoAccessor.findByCustomer(customer);
    }

    @Override
    public Account createAccount(final CurrencyType currency) {
        log.info("Creating account for currency {} with account balance {}", currency, INITIAL_ACCOUNT_BALANCE);
        Account account = Account.builder()
                .balance(INITIAL_ACCOUNT_BALANCE)
                .currency(currency)
                .build();

        return accountRepoAccessor.save(account);
    }
}
