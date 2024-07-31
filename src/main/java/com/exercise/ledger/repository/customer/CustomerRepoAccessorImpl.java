package com.exercise.ledger.repository.customer;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.exercise.ledger.core.customer.Customer;
import com.exercise.ledger.exception.customer.CustomerNotFoundException;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class CustomerRepoAccessorImpl implements CustomerRepoAccessor {

    private final CustomerRepository customerRepository;

    @Override
    public Customer findById(final UUID customerId) {
        log.info("Finding customer record for customerId {}", customerId);
        Optional<Customer> customer = customerRepository.findById(customerId);
        if (customer.isEmpty()) {
            throw new CustomerNotFoundException(String.format("Customer with id %s not found", customerId));
        }

        log.info("Customer record found {}", customer.get());
        return customer.get();
    }

    @Override
    public Customer save(final Customer customer) {
        log.info("Creating customer {}", customer);
        Customer createdCustomer = customerRepository.save(customer);

        log.info("Customer record after creation: {}", createdCustomer);
        return createdCustomer;
    }

    @Override
    public boolean isEmailRegistered(final String email) {
        boolean isEmailRegistered = !customerRepository.findByEmailIgnoreCase(email).isEmpty();
        log.info("Is {} email registered: {}", email, isEmailRegistered);

        return isEmailRegistered;
    }

    @Override
    public boolean isUserNameRegistered(String userName) {
        boolean isUserNameRegistered = !customerRepository.findByUserNameIgnoreCase(userName).isEmpty();
        log.info("Is {} userName registered: {}", userName, isUserNameRegistered);

        return isUserNameRegistered;
    }
}
