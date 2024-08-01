package com.exercise.ledger.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.exercise.ledger.core.account.Account;
import com.exercise.ledger.core.account.AccountDTO;
import com.exercise.ledger.core.account.CurrencyType;
import com.exercise.ledger.core.customer.Customer;
import com.exercise.ledger.core.customer.CustomerDTO;
import com.exercise.ledger.core.transaction.Transaction;
import com.exercise.ledger.core.transaction.TransactionDTO;
import com.exercise.ledger.mapper.Mappers;
import com.exercise.ledger.service.account.AccountService;
import com.exercise.ledger.service.customer.CustomerService;
import com.exercise.ledger.service.transaction.TransactionService;
import com.exercise.ledger.utils.TransactionUtils;

import java.util.List;
import java.util.UUID;

@RestController
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CustomerController {

    private final AccountService accountService;
    private final CustomerService customerService;
    private final TransactionService transactionService;

    @GetMapping("/customers/{customerId}/accounts")
    public List<AccountDTO> getAccounts(@PathVariable UUID customerId, @Nullable @RequestParam CurrencyType currency) {
        log.info("getAccounts request for customerId {}", customerId);
        List<Account> userAccounts = currency == null ? accountService.getAllAccounts(customerId)
                : accountService.getCurrencyAccounts(customerId, currency);
        List<AccountDTO> accounts = userAccounts
                .stream()
                .map(Mappers::mapToAccountDTO)
                .toList();
        log.info("Accounts after DTO conversion for user {}: \n {}", customerId, accounts);
        return accounts;
    }

    @PostMapping("/customers")
    public CustomerDTO createCustomer(@Valid @RequestBody CustomerDTO customerDTO) {
        log.info("createCustomer request object: {}", customerDTO);
        Customer customer = Mappers.mapToCustomer(customerDTO);
        log.info("Customer object after Mapping from DTO: {}", customer);
        Customer createdCustomer = customerService.createCustomer(customer);
        CustomerDTO createdCustomerDTO = Mappers.mapToCustomerDTO(createdCustomer);
        log.info("Customer record returned {}", createdCustomerDTO);
        return createdCustomerDTO;
    }

    @PostMapping("/customers/{customerId}/transaction")
    public TransactionDTO createTransaction(@Valid @RequestBody TransactionDTO transactionDTO,
            @PathVariable UUID customerId) {
        log.info("Transaction request {} for customerId {}", transactionDTO, customerId);
        transactionDTO.setCustomerId(customerId);
        transactionDTO.setDirection(TransactionUtils.getDirection(transactionDTO.getType()));
        log.info("Transaction request after updates {}", transactionDTO, customerId);
        Transaction transaction = transactionService.createTransaction(Mappers.mapToTransaction(transactionDTO));
        TransactionDTO createdTransactionDTO = Mappers.mapToTransactionDTO(transaction);
        log.info("Transaction returned {}", createdTransactionDTO);
        return createdTransactionDTO;
    }
}
