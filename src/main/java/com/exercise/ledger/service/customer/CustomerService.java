package com.exercise.ledger.service.customer;

import java.util.UUID;

import com.exercise.ledger.core.customer.Customer;

public interface CustomerService {

    Customer createCustomer(Customer customer);

    Customer getCustomer(final UUID customerId);
}
