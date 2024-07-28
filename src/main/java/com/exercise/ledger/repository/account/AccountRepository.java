package com.exercise.ledger.repository.account;

import org.springframework.data.jpa.repository.JpaRepository;
import com.exercise.ledger.core.account.Account;
import java.util.List;



public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> findByCustomerId(long customerId);
}
