
# Basic Ledger Service

This service provides REST APIs to perform following basic ledger operations.

1. Creation of users.
1. Deposits for users in any currency.
1. Transfers between users in any currency.
1. Listing of balances.
1. Withdrawals by users.

## Sevice structure

### Database

This service's database has 3 tables.

1. Customer
2. Account
3. Transaction

**Customer Table**: This table holds customer information like userName, email and has OneToMany mapping with Account as well as Transaction table. Whenever we create new user, we create all currency accounts for that user with `0.0` initial balance.

**Account Table**: This table holds account information like CurrencyType (Bitcoin, Ethereum, Matic) and balance.

**Transaction Table**: This table holds all the transaction details. For Withdrow or Deposit, we create one transaction and map it to customer. For Transfer type transaction, we create 2 records as it involves 2 customers and we add 1 transaction to each customer.

### API Details

#### @GetMapping("/customers/{customerId}/accounts")

This GET API returns all the accounts associated with given customer. For specific currency account, we can pass currency filter in URL (`/customers/{customerId}/accounts?currency={currencyValue}`) to get that account.

#### @PostMapping("/customers")

This POST API creates customer and returns customer object after creation. Along side of creating record in Customer Table, this also creates currency accounts for the customer.

#### @PostMapping("/customers/{customerId}/transaction")

This POST API is responsible for creating 3 types of transactions, Withdraw, Deposit and Transfer for given customerId.

## Developer setup

### Pre-requisits

1. Install JDK 17
1. Install [Postman](https://www.postman.com/downloads/) or any other tool which can help you to make API calls.

### Commands

1. Clean build: `./mvnw clean install`
2. Run server: `./mvnw spring-boot:run`
3. Run using docker:
    1. cd to the folder of `Dockerfile`.
    2. build docker image using: `docker build -t ledgerapi .`
    3. run docker image: `docker run -p 8000:8080 ledgerapi`

### Getting started

1. Run `./mvnw spring-boot:run` to start server. Server will be running on `8080` port.
1. View H2 Database records
    1. Open <http://localhost:8080/h2-console/> in browser
    1. Change **JDBC URL** to `jdbc:h2:mem:ledgerdb`
    1. Click on `Connect` button
    1. Run following queries to see default records in H2 database.

        ```
        select * from account;
        select * from customer;
        select * from transaction;
        ```

    1. Note: These default records are added from `src\resources\import.sql` file
1. To Test APIs, import requests located at `postman\LedgerPostmanRequests.json` in Postman app and start running it.
1. To check unit test coverage, after build open `target\site\index.html` file.
