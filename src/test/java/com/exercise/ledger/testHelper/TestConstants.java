package com.exercise.ledger.testHelper;

import lombok.experimental.UtilityClass;

import java.util.UUID;

@UtilityClass
public class TestConstants {
    public static final UUID VALID_CUSTOMER_ID = UUID.fromString("31ffd3ff-1e95-4c28-992c-f4c3e2a6f277");
    public static final UUID INVALID_CUSTOMER_ID = UUID.randomUUID();
    public static final String REGISTERED_EMAIL = "djokoviC@gmail.com";
    public static final String UNREGISTERED_EMAIL = "abc@gmail.com";
    public static final String TEST_EMAIL = "test@ledger.com";
    public static final String REGISTERED_USER_NAME = "djokoviC";
    public static final String UNREGISTERED_USER_NAME = "abc";
    public static final String TEST_USER_NAME = "test";
    public static final UUID TRANSACTION_NUMBER = UUID.randomUUID();
}
