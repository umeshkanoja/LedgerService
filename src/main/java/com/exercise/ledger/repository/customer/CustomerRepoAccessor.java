package com.exercise.ledger.repository.customer;

import com.exercise.ledger.core.customer.Customer;

import java.util.UUID;

public interface CustomerRepoAccessor {

    Customer findById(final UUID customerId);

    Customer create(final Customer customer);

    boolean isEmailRegistered(final String email);

    boolean isUserNameRegistered(final String userName);
}
