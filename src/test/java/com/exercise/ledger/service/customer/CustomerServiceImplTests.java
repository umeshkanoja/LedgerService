package com.exercise.ledger.service.customer;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.exercise.ledger.core.account.CurrencyType;
import com.exercise.ledger.core.customer.Customer;
import com.exercise.ledger.exception.customer.FieldAlreadyTakenException;
import com.exercise.ledger.repository.account.AccountRepoAccessor;
import com.exercise.ledger.repository.customer.CustomerRepoAccessor;
import com.exercise.ledger.testHelper.TestObjectBuilder;

import java.util.List;
import java.util.stream.Stream;

import static com.exercise.ledger.testHelper.TestConstants.TEST_EMAIL;
import static com.exercise.ledger.testHelper.TestConstants.TEST_USER_NAME;
import static com.exercise.ledger.testHelper.TestConstants.VALID_CUSTOMER_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTests {
    @Mock
    private AccountRepoAccessor mockAccountRepoAccessor;

    @Mock
    private CustomerRepoAccessor mockCustomerRepoAccessor;

    @InjectMocks
    private CustomerServiceImpl customerServiceImpl;

    private static Stream<Arguments> provideValuesForCreateCustomerValidations() {
        return Stream.of(
                Arguments.of(true, false),
                Arguments.of(false, true),
                Arguments.of(true, true));
    }

    @Test
    void testCreateCustomer() {
        Customer customer = TestObjectBuilder.buildCustomer();

        when(mockCustomerRepoAccessor.isEmailRegistered(TEST_EMAIL)).thenReturn(false);
        when(mockCustomerRepoAccessor.isUserNameRegistered(TEST_USER_NAME)).thenReturn(false);
        when(mockCustomerRepoAccessor.create(customer)).thenReturn(customer);
        when(mockAccountRepoAccessor.create(any(CurrencyType.class))).thenAnswer(invocation -> {
            CurrencyType currency = invocation.getArgument(0);
            return TestObjectBuilder.buildAccount(currency);
        });

        Customer actualCustomer = customerServiceImpl.createCustomer(customer);
        List<CurrencyType> accountCurrencies = actualCustomer.getAccounts()
                .stream()
                .map(account -> account.getCurrency())
                .toList();
        assertIterableEquals(List.of(CurrencyType.values()), accountCurrencies);
    }

    @ParameterizedTest
    @MethodSource("provideValuesForCreateCustomerValidations")
    void testCreateCustomerValidations(boolean isEmailRegistered, boolean isUserNameRegistered) {
        Customer customer = TestObjectBuilder.buildCustomer();

        when(mockCustomerRepoAccessor.isEmailRegistered(TEST_EMAIL)).thenReturn(isEmailRegistered);
        lenient().when(mockCustomerRepoAccessor.isUserNameRegistered(TEST_USER_NAME)).thenReturn(isUserNameRegistered);

        assertThrows(FieldAlreadyTakenException.class, () -> customerServiceImpl.createCustomer(customer));
    }

    @Test
    void testGetCustomer() {
        Customer expectedCustomer = TestObjectBuilder.buildCustomer();

        when(mockCustomerRepoAccessor.findById(VALID_CUSTOMER_ID)).thenReturn(expectedCustomer);

        Customer actualCustomer = customerServiceImpl.getCustomer(VALID_CUSTOMER_ID);

        assertEquals(expectedCustomer, actualCustomer);
    }
}
