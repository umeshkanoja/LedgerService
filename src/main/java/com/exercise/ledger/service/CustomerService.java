package com.exercise.ledger.service;

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
public class CustomerService {
    private final CustomerRepoAccessor customerRepoAccessor;
    private final AccountRepoAccessor accountRepoAccessor;

    public Customer addCustomer(Customer customer) {
        Customer newCustomer = customerRepoAccessor.save(customer);
        addAccounts(newCustomer);
        customerRepoAccessor.save(newCustomer);
        // log.info("New customer created: {}", newCustomer);
        return newCustomer;
    }

    public Customer getCustomer(final long customerId) {
        Customer customer = customerRepoAccessor.findById(customerId);

        return customer;
    }

    private void addAccounts(Customer customer) {
        for (CurrencyType currency : CurrencyType.values()) {
            customer.addAccount(getAccount(currency, customer));
        }
    }

    private Account getAccount(CurrencyType currency, Customer customer) {
        Account account = Account.builder()
                .balance(0.0)
                .currency(currency)
                .id(0l)
                .build();

        Account createdAccount = accountRepoAccessor.save(account);

        return createdAccount;
    }
}
