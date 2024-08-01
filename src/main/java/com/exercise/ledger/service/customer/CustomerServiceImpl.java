package com.exercise.ledger.service.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.exercise.ledger.core.account.CurrencyType;
import com.exercise.ledger.core.customer.Customer;
import com.exercise.ledger.exception.customer.FieldAlreadyTakenException;
import com.exercise.ledger.repository.account.AccountRepoAccessor;
import com.exercise.ledger.repository.customer.CustomerRepoAccessor;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepoAccessor customerRepoAccessor;

    private final AccountRepoAccessor accountRepoAccessor;

    @Override
    @Transactional
    public Customer createCustomer(final Customer customer) {
        validateCustomerObject(customer);
        Customer newCustomer = customerRepoAccessor.create(customer);
        createAccounts(newCustomer);
        newCustomer = customerRepoAccessor.create(newCustomer);
        log.info("New customer created and accounts linked to it");
        return newCustomer;
    }

    @Override
    public Customer getCustomer(final UUID customerId) {
        log.info("Get customer record for customerId {}", customerId);
        return customerRepoAccessor.findById(customerId);
    }

    private void createAccounts(final Customer customer) {
        log.info("Creating accounts for customer {}", customer);
        for (CurrencyType currency : CurrencyType.values()) {
            customer.addAccount(accountRepoAccessor.create(currency));
        }
    }

    private void validateCustomerObject(final Customer customer) {
        log.info("Validating customer object {}", customer);
        if (customerRepoAccessor.isEmailRegistered(customer.getEmail())) {
            throw new FieldAlreadyTakenException("Email already registered");
        }

        if (customerRepoAccessor.isUserNameRegistered(customer.getUserName())) {
            throw new FieldAlreadyTakenException("UserName already registered");
        }
    }
}
