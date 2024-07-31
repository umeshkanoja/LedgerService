package com.exercise.ledger.repository.customer;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exercise.ledger.core.customer.Customer;
import java.util.List;


public interface CustomerRepository extends JpaRepository<Customer, UUID> {
    List<Customer> findByEmailIgnoreCase(String email);
    List<Customer> findByUserNameIgnoreCase(String userName);
}
