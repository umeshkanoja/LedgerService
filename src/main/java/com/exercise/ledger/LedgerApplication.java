package com.exercise.ledger;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.exercise.ledger.core.customer.Customer;
import com.exercise.ledger.service.customer.CustomerServiceImpl;

@SpringBootApplication
public class LedgerApplication implements CommandLineRunner {

	@Autowired
	CustomerServiceImpl customerService;

	public static void main(String[] args) {
		SpringApplication.run(LedgerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		customerService
				.addCustomer(buildCustomer("Djokovic", "Djokovic@gmail.com"));
		customerService
				.addCustomer(buildCustomer("Monfils", "Monfils@gmail.com"));
		customerService.addCustomer(buildCustomer("Isner", "Isner@gmail.com"));
	}

	private static Customer buildCustomer(final String userName, final String email) {
		return Customer.builder()
				.userName(userName)
				.email(email)
				.accounts(new ArrayList<>())
				.transactions(new ArrayList<>())
				.build();
	}
}
