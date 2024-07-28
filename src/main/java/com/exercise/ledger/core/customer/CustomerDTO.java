package com.exercise.ledger.core.customer;

import java.util.List;

import com.exercise.ledger.core.account.AccountDTO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class CustomerDTO {
    private Long id;
    private String userName;
    private String email; 
    private List<AccountDTO> accounts;
}
