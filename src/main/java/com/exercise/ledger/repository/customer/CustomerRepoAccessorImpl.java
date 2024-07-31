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
    public Customer findById(UUID customerId) {
        Optional<Customer> customer = customerRepository.findById(customerId);
        if (customer.isEmpty()) {
            log.warn("Customer not found for customer id: {}", customerId);
            throw new CustomerNotFoundException(String.format("Customer with id %s not found", customerId));
        }

        return customerRepository.findById(customerId).get();
    }

    @Override
    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public boolean isEmailRegistered(String email) {
        return !customerRepository.findByEmailIgnoreCase(email).isEmpty();
    }

    @Override
    public boolean isUserNameRegistered(String userName) {
        return !customerRepository.findByUserNameIgnoreCase(userName).isEmpty();
    }
}
