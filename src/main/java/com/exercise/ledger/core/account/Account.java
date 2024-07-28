package com.exercise.ledger.core.account;

import com.exercise.ledger.core.customer.Customer;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
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

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Account {
    @Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
    @Column(name="customer_id", updatable=false, insertable=false)
    private Long customerId;
    @ManyToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE, 
        CascadeType.DETACH,  CascadeType.REFRESH})
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    @ToString.Exclude
    private Customer customer;
    private Double balance;
    @Enumerated(EnumType.STRING)
    private CurrencyType currency;
}