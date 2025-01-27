package com.exercise.ledger.core.transaction;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import com.exercise.ledger.core.account.CurrencyType;
import com.exercise.ledger.core.common.Audit;
import com.exercise.ledger.core.customer.Customer;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID transactionId;

    private UUID transactionNumber;

    @Column(name = "customer_id", updatable = false, insertable = false)
    private UUID customerId;

    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH })
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    @ToString.Exclude
    private Customer customer;

    private UUID withCustomerId;

    @Enumerated(EnumType.STRING)
    private TransactionType type;

    @Enumerated(EnumType.STRING)
    private TransactionDirection direction;

    private Double amount;

    @Enumerated(EnumType.STRING)
    private CurrencyType currency;

    @Embedded
    @ToString.Exclude
    private Audit audit;
}
