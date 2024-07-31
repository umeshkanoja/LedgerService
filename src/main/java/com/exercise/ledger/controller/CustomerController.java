package com.exercise.ledger.controller;

import org.springframework.web.bind.annotation.RestController;

import com.exercise.ledger.core.account.Account;
import com.exercise.ledger.core.account.AccountDTO;
import com.exercise.ledger.core.customer.Customer;
import com.exercise.ledger.core.customer.CustomerDTO;
import com.exercise.ledger.core.transaction.Transaction;
import com.exercise.ledger.core.transaction.TransactionDTO;
import com.exercise.ledger.mapper.Mappers;
import com.exercise.ledger.service.account.AccountService;
import com.exercise.ledger.service.customer.CustomerService;
import com.exercise.ledger.service.transaction.TransactionService;
import com.exercise.ledger.utils.TransactionUtils;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CustomerController {
    private final AccountService accountService;
    private final CustomerService customerService;
    private final TransactionService transactionService;

    @GetMapping("/customers/{id}/accounts")
    public List<AccountDTO> getAccounts(@PathVariable(value = "id") UUID customerId) {
        List<Account> userAccounts = accountService.getAllAccounts(customerId);
        List<AccountDTO> accounts = userAccounts
                .stream()
                .map(Mappers::mapToAccountDTO)
                .toList();
        log.info("Accounts after DTO conversion for user {}: \n {}", customerId, accounts);
        return accounts;
    }

    @PostMapping("/customers")
    public CustomerDTO postMethodName(@Valid @RequestBody CustomerDTO customerDTO) {
        // Setting id to 0 for generating newId during saving in DB
        log.info("Request body customer object: {}", customerDTO);
        Customer customer = Mappers.mapToCustomer(customerDTO);
        Customer createCustomer = customerService.createCustomer(customer);
        return Mappers.mapToCustomerDTO(createCustomer);
    }

    @PostMapping("/customers/{customerId}/transaction")
    public TransactionDTO postMethodName(@Valid @RequestBody TransactionDTO transactionDTO,
            @PathVariable UUID customerId) {
        transactionDTO.setCustomerId(customerId);
        transactionDTO.setDirection(TransactionUtils.getDirection(transactionDTO.getType()));
        log.info("transaction {} \n customerId {}", transactionDTO, customerId);
        Transaction transaction = transactionService.createTransaction(Mappers.mapToTransaction(transactionDTO));
        return Mappers.mapToTransactionDTO(transaction);
    }
}
