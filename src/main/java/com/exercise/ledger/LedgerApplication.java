package com.exercise.ledger;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.exercise.ledger.core.customer.Customer;
import com.exercise.ledger.service.CustomerService;

@SpringBootApplication
public class LedgerApplication implements CommandLineRunner {

	@Autowired
	CustomerService customerService;
	public static void main(String[] args) {
		SpringApplication.run(LedgerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		customerService.addCustomer(new Customer(0l, "Djokovic", "Djokovic@gmail.com", new ArrayList<>(), new ArrayList<>()));
		customerService.addCustomer(new Customer(0l, "Monfils", "Monfils@gmail.com", new ArrayList<>(), new ArrayList<>()));
		customerService.addCustomer(new Customer(0l, "Isner", "Isner@gmail.com", new ArrayList<>(), new ArrayList<>()));
	}
}
