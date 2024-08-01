package com.exercise.ledger.core.customer;

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

import com.exercise.ledger.core.account.Account;
import com.exercise.ledger.core.common.Audit;
import com.exercise.ledger.core.transaction.Transaction;

import java.util.List;
import java.util.UUID;

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

    // Excluding userName from toString to avoid logging personal data.
    @ToString.Exclude
    @Column(unique = true, nullable = false)
    private String userName;

    // Excluding email from toString to avoid logging personal data.
    @ToString.Exclude
    @Column(unique = true, nullable = false)
    private String email;

    @ToString.Exclude
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Account> accounts;

    @ToString.Exclude
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<Transaction> transactions;

    @Embedded
    @ToString.Exclude
    private Audit audit;

    public void addAccount(Account account) {
        accounts.add(account);
        account.setCustomer(this);
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
        transaction.setCustomer(this);
    }
}
