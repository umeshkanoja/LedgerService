package com.exercise.ledger.repository.account;

import org.springframework.data.jpa.repository.JpaRepository;
import com.exercise.ledger.core.account.Account;
import java.util.List;
import java.util.UUID;
import com.exercise.ledger.core.customer.Customer;

public interface AccountRepository extends JpaRepository<Account, UUID> {

    List<Account> findByCustomer(Customer customer);
}
