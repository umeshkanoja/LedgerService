package com.exercise.ledger.repository.customer;

import java.util.UUID;

import com.exercise.ledger.core.customer.Customer;

public interface CustomerRepoAccessor {

    Customer findById(final UUID customerId);

    Customer save(final Customer customer);
}
