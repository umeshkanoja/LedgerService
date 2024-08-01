package com.exercise.ledger.core.customer;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import com.exercise.ledger.core.account.AccountDTO;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@ToString
public class CustomerDTO {

    private UUID id;

    @NotBlank(message = "The username is required.")
    @Size(min = 3, max = 20, message = "The username must be from 3 to 20 characters.")
    @ToString.Exclude
    private String userName;

    @NotEmpty(message = "The email is required.")
    @Email(message = "The email is not a valid email.")
    @ToString.Exclude
    private String email;

    private List<AccountDTO> accounts;
}
