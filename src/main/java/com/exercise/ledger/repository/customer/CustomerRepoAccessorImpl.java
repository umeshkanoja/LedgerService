package com.exercise.ledger.repository.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.exercise.ledger.core.customer.Customer;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class CustomerRepoAccessorImpl implements CustomerRepoAccessor {

    private final CustomerRepository customerRepository;

    @Override
    public Customer findById(long customerId) {
        return customerRepository.findById(customerId).get();
    }

    @Override
    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }
    
}
