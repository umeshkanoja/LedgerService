package com.exercise.ledger.repository.customer;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exercise.ledger.core.customer.Customer;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {

}
