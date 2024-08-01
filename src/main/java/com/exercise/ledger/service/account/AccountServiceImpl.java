package com.exercise.ledger.service.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.exercise.ledger.core.account.Account;
import com.exercise.ledger.core.account.CurrencyType;
import com.exercise.ledger.core.customer.Customer;
import com.exercise.ledger.repository.account.AccountRepoAccessor;
import com.exercise.ledger.repository.customer.CustomerRepoAccessor;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AccountServiceImpl implements AccountService {

    private final AccountRepoAccessor accountRepoAccessor;

    private final CustomerRepoAccessor customerRepoAccessor;

    @Override
    public List<Account> getAllAccounts(final UUID customerId) {
        log.info("Getting all accounts of customer {}", customerId);
        Customer customer = customerRepoAccessor.findById(customerId);

        return accountRepoAccessor.findByCustomer(customer);
    }

    @Override
    public List<Account> getCurrencyAccounts(UUID customerId, CurrencyType currency) {
        log.info("Getting currency {} accounts of customer {}", currency, customerId);
        Customer customer = customerRepoAccessor.findById(customerId);

        return accountRepoAccessor.findByCustomerAndCurrency(customer, currency);
    }
}
