package com.exercise.ledger.repository.customer;

import com.exercise.ledger.core.customer.Customer;

public interface CustomerRepoAccessor {
    Customer findById(final long customerId);
    Customer save(final Customer customer);
}
