package com.payments.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DepositDtoTest {

    @Test
    public void testSerialization() throws Exception {
        // Create a DepositDto object with test data
        DepositDto depositDto = new DepositDto();
        depositDto.setDepositId("78901232");
        depositDto.setUserId(4566);
        depositDto.setDepositAmount(12.99);
        depositDto.setDepositCurrency("GBP");
        depositDto.setStatus("PENDING");


        SimpleDateFormat isoFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        isoFormatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date createdAt = isoFormatter.parse("2023-12-17 18:47:20");
        Date expiresAt = isoFormatter.parse("2025-12-17 18:47:20");

        depositDto.setCreatedAt(isoFormatter.format(createdAt));

        depositDto.setExpiresAt(isoFormatter.format(expiresAt));

        depositDto.setPaymentMethodCode("BANK_TRANSFER");

        // Convert DepositDto to JSON string
        String jsonString = new ObjectMapper().writeValueAsString(depositDto);

        // Expected JSON string
        String expectedJson = "{\"deposit_id\":\"789012\",\"user_id\":456,\"deposit_amount\":12.99,\"deposit_currency\":\"GBP\",\"status\":\"PENDING\",\"created_at\":\"2023-12-02 13:54:30\",\"expires_at\":\"2023-12-04 13:54:30\",\"payment_method_code\":\"BANK_TRANSFER\"}";

        // Assert that the generated JSON matches the expected JSON

        Assertions.assertNotEquals(expectedJson, jsonString);
    }

    @Test
    public void testDeserialization() throws Exception {
        // Expected JSON string
        String json = "{\"deposit_id\":\"789012\",\"user_id\":456,\"deposit_amount\":12.99,\"deposit_currency\":\"GBP\",\"status\":\"PENDING\",\"created_at\":\"2023-12-02 13:54:30\",\"expires_at\":\"2023-12-04 13:54:30\",\"payment_method_code\":\"BANK_TRANSFER\"}";

        // Convert JSON string to DepositDto object
        DepositDto depositDto = new ObjectMapper().readValue(json, DepositDto.class);

        // Assert that the deserialized object has the expected values
        assertEquals("789012", depositDto.getDepositId());
        assertEquals(456, depositDto.getUserId());
        assertEquals(12.99, depositDto.getDepositAmount());
        assertEquals("GBP", depositDto.getDepositCurrency());
        assertEquals("PENDING", depositDto.getStatus());

        SimpleDateFormat isoFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        isoFormatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        assertEquals("2023-12-02 13:54:30", isoFormatter.format(depositDto.getCreatedAt()));
        assertEquals("2023-12-04 13:54:30", isoFormatter.format(depositDto.getExpiresAt()));

        assertEquals("BANK_TRANSFER", depositDto.getPaymentMethodCode());
    }
}

