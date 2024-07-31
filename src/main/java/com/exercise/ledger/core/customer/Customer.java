package com.exercise.ledger.core.customer;

import java.util.List;
import java.util.UUID;

import com.exercise.ledger.core.account.Account;
import com.exercise.ledger.core.common.Audit;
import com.exercise.ledger.core.transaction.Transaction;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true, nullable = false)
    private String userName;

    @Column(unique = true, nullable = false)
    private String email;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Account> accounts;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<Transaction> transactions;

    @Embedded
    private Audit audit;

    public Account addAccount(Account account) {
        accounts.add(account);
        account.setCustomer(this);
        return account;
    }

    public Transaction addTransaction(Transaction transaction) {
        transactions.add(transaction);
        transaction.setCustomer(this);
        return transaction;
    }
}
