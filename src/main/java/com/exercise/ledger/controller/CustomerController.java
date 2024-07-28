package com.exercise.ledger.controller;

import org.springframework.web.bind.annotation.RestController;

import com.exercise.ledger.core.account.Account;
import com.exercise.ledger.core.account.AccountDTO;
import com.exercise.ledger.core.customer.Customer;
import com.exercise.ledger.core.customer.CustomerDTO;
import com.exercise.ledger.core.transaction.TransactionDTO;
import com.exercise.ledger.mapper.Mappers;
import com.exercise.ledger.service.AccountService;
import com.exercise.ledger.service.CustomerService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

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

    @GetMapping("/customers/{id}/accounts")
    public List<AccountDTO> getAccounts(@PathVariable(value = "id") int userId) {
        List<Account> userAccounts = accountService.getAllAccounts(userId);
        List<AccountDTO> accounts = userAccounts
                .stream()
                .map(Mappers::mapToAccountDTO)
                .toList();
        log.info("Accounts after DTO conversion for user {}: \n {}", userId, accounts);
        return accounts;
    }

    @PostMapping("/customers")
    public CustomerDTO postMethodName(@RequestBody CustomerDTO customerDTO) {
        // Setting id to 0 for generating newId and to avoid matching it to other ids
        customerDTO.setId(0L);
        log.info("Request body customer object: {}", customerDTO);
        Customer customer = Mappers.mapToCustomer(customerDTO);
        Customer createCustomer = customerService.addCustomer(customer);
        return Mappers.mapToCustomerDTO(createCustomer);
    }
    

    @PostMapping("/customers/{customerId}/transaction")
    public String postMethodName(@RequestBody TransactionDTO transactionDTO, @PathVariable long customerId) {
        transactionDTO.setCustomerId(customerId);
        transactionDTO.setId(0L);
        
        return "entity";
    }
    
}
