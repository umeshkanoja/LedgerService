package com.exercise.ledger.service.customer;

import com.exercise.ledger.core.customer.Customer;

import java.util.UUID;

public interface CustomerService {

    Customer createCustomer(Customer customer);

    Customer getCustomer(final UUID customerId);
}
