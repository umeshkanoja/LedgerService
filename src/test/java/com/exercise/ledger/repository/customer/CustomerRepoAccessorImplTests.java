package com.exercise.ledger.repository.customer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.exercise.ledger.core.customer.Customer;
import com.exercise.ledger.exception.customer.CustomerNotFoundException;
import com.exercise.ledger.testHelper.TestObjectBuilder;

import java.util.List;
import java.util.stream.Stream;

import static com.exercise.ledger.testHelper.TestConstants.INVALID_CUSTOMER_ID;
import static com.exercise.ledger.testHelper.TestConstants.REGISTERED_EMAIL;
import static com.exercise.ledger.testHelper.TestConstants.REGISTERED_USER_NAME;
import static com.exercise.ledger.testHelper.TestConstants.UNREGISTERED_EMAIL;
import static com.exercise.ledger.testHelper.TestConstants.UNREGISTERED_USER_NAME;
import static com.exercise.ledger.testHelper.TestConstants.VALID_CUSTOMER_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;;

@DataJpaTest
class CustomerRepoAccessorImplTests {
    @Autowired
    private CustomerRepository customerRepository;

    private CustomerRepoAccessor customerRepoAccessor;

    private static Stream<Arguments> provideEmailsForIsEmailRegistered() {
        return Stream.of(
                Arguments.of(REGISTERED_EMAIL, true),
                Arguments.of(UNREGISTERED_EMAIL, false));
    }

    private static Stream<Arguments> provideUserNamesForIsUserNameRegistered() {
        return Stream.of(
                Arguments.of(REGISTERED_USER_NAME, true),
                Arguments.of(UNREGISTERED_USER_NAME, false));
    }

    @BeforeEach
    void init() {
        this.customerRepoAccessor = new CustomerRepoAccessorImpl(customerRepository);
    }

    @Test
    void testFindById() {
        Customer customer = customerRepoAccessor.findById(VALID_CUSTOMER_ID);

        assertEquals(VALID_CUSTOMER_ID, customer.getId());
        assertEquals(3, customer.getAccounts().size());
        assertFalse(customer.getEmail().isBlank());
    }

    @Test
    void testFindByIdThrowsException() {
        assertThrows(CustomerNotFoundException.class, () -> customerRepoAccessor.findById(INVALID_CUSTOMER_ID));
    }

    @Test
    void testCustomerCreation() {
        Customer customer = customerRepoAccessor.create(TestObjectBuilder.buildCustomer());

        List<Customer> customers = customerRepository.findAll();

        assertTrue(customers.contains(customer));
    }

    @ParameterizedTest
    @MethodSource("provideEmailsForIsEmailRegistered")
    void testIsEmailRegistered(String email, boolean expected) {
        boolean actual = customerRepoAccessor.isEmailRegistered(email);
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @MethodSource("provideUserNamesForIsUserNameRegistered")
    void testIsUserNameRegistered(String userName, boolean expected) {
        boolean actual = customerRepoAccessor.isUserNameRegistered(userName);
        assertEquals(expected, actual);
    }
}
