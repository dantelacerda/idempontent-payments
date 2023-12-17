package com.payments.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PaymentParametersDTOTest {

    @Test
    public void testSerialization() throws Exception {
        // Create a PaymentParametersDTO object with test data
        String paymentType = "CREDIT_CARD";
        String paymentId = "123456";

        PaymentParametersDTO paymentParametersDTO = new PaymentParametersDTO();
        paymentParametersDTO.setPaymentType(paymentType);
        paymentParametersDTO.setPaymentId(paymentId);

        // Convert PaymentParametersDTO to JSON string
        String jsonString = new ObjectMapper().writeValueAsString(paymentParametersDTO);

        // Expected JSON string
        String expectedJson = "{\"paymentType\":\"CREDIT_CARD\",\"paymentId\":\"123456\"}";

        // Assert that the generated JSON matches the expected JSON
        assertEquals(expectedJson, jsonString);
    }

    @Test
    public void testDeserialization() throws Exception {
        // Expected JSON string
        String json = "{\"paymentType\":\"CREDIT_CARD\",\"paymentId\":\"123456\"}";

        // Convert JSON string to PaymentParametersDTO object
        PaymentParametersDTO paymentParametersDTO = new ObjectMapper().readValue(json, PaymentParametersDTO.class);

        // Assert that the deserialized object has the expected values
        assertEquals("CREDIT_CARD", paymentParametersDTO.getPaymentType());
        assertEquals("123456", paymentParametersDTO.getPaymentId());
    }
}

