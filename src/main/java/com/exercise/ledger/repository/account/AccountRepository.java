package com.exercise.ledger.repository.account;

import org.springframework.data.jpa.repository.JpaRepository;
import com.exercise.ledger.core.account.Account;
import java.util.List;
import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, UUID> {
    List<Account> findByCustomerId(UUID customerId);
}
