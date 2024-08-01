package com.exercise.ledger.repository.account;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exercise.ledger.core.account.Account;
import com.exercise.ledger.core.account.CurrencyType;
import com.exercise.ledger.core.customer.Customer;

import java.util.List;
import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, UUID> {

    List<Account> findByCustomer(Customer customer);

    List<Account> findByCustomerAndCurrency(Customer customer, CurrencyType currency);
}
