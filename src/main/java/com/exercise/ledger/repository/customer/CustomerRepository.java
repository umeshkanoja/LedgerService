package com.exercise.ledger.repository.customer;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exercise.ledger.core.customer.Customer;

import java.util.List;
import java.util.UUID;


public interface CustomerRepository extends JpaRepository<Customer, UUID> {
    List<Customer> findByEmailIgnoreCase(String email);
    List<Customer> findByUserNameIgnoreCase(String userName);
}
