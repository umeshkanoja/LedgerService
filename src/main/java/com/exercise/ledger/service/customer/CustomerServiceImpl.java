package com.exercise.ledger.service.customer;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exercise.ledger.core.account.CurrencyType;
import com.exercise.ledger.core.customer.Customer;
import com.exercise.ledger.exception.customer.FieldAlreadyTakenException;
import com.exercise.ledger.repository.customer.CustomerRepoAccessor;
import com.exercise.ledger.service.account.AccountService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepoAccessor customerRepoAccessor;

    private final AccountService accountService;

    @Override
    @Transactional
    public Customer createCustomer(Customer customer) {
        validateCustomerObject(customer);
        Customer newCustomer = customerRepoAccessor.save(customer);
        createAccounts(newCustomer);
        newCustomer = customerRepoAccessor.save(newCustomer);
        log.info("New customer created: {}", newCustomer);
        return newCustomer;
    }

    @Override
    public Customer getCustomer(final UUID customerId) {
        Customer customer = customerRepoAccessor.findById(customerId);

        return customer;
    }

    private void createAccounts(Customer customer) {
        for (CurrencyType currency : CurrencyType.values()) {
            customer.addAccount(accountService.createAccount(currency));
        }
    }

    private void validateCustomerObject(final Customer customer) {
        if (customerRepoAccessor.isEmailRegistered(customer.getEmail())) {
            throw new FieldAlreadyTakenException("Email already registered");
        }

        if (customerRepoAccessor.isUserNameRegistered(customer.getUserName())) {
            throw new FieldAlreadyTakenException("UserName already registered");
        }
    }
}
