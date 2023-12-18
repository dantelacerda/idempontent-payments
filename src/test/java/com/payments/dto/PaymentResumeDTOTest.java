package com.payments.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PaymentResumeDTOTest {

    @Test
    public void testSerialization() throws Exception {
        // Create a PaymentResumeDTO object with test data
        String paymentId = "123456";
        String paymentType = "CREDIT_CARD";
        String paymentStatus = "COMPLETED";
        double transferAmount = 15.99;
        String transferCurrency = "USD";
        Date createdAt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2023-12-17 18:47:20");

        PaymentResumeDTO paymentResumeDTO = new PaymentResumeDTO(paymentId, paymentType, paymentStatus,
                transferAmount, transferCurrency, createdAt);

        // Convert PaymentResumeDTO to JSON string
        String jsonString = new ObjectMapper().writeValueAsString(paymentResumeDTO);

        // Expected JSON string
        String expectedJson = "{\"payment_id\":\"123456\",\"payment_type\":\"CREDIT_CARD\",\"status\":\"COMPLETED\",\"transfer_amount\":15.99,\"transfer_currency\":\"USD\",\"created_at\":\"2023-12-17 21:47:20\"}";

        // Assert that the generated JSON matches the expected JSON
        assertEquals(expectedJson, jsonString);
    }

    @Test
    public void testDeserialization() throws Exception {
        // Expected JSON string
        String json = "{\"payment_id\":\"123456\",\"payment_type\":\"CREDIT_CARD\",\"status\":\"COMPLETED\",\"transfer_amount\":15.99,\"transfer_currency\":\"USD\",\"created_at\":\"2023-12-17 21:47:20\"}";

        // Convert JSON string to PaymentResumeDTO object
        PaymentResumeDTO paymentResumeDTO = new ObjectMapper().readValue(json, PaymentResumeDTO.class);

        // Assert that the deserialized object has the expected values
        assertEquals("123456", paymentResumeDTO.getPaymentId());
        assertEquals("CREDIT_CARD", paymentResumeDTO.getPaymentType());
        assertEquals("COMPLETED", paymentResumeDTO.getPaymentStatus());
        assertEquals(15.99, paymentResumeDTO.getTransferAmount());
        assertEquals("USD", paymentResumeDTO.getTransferCurrency());

        SimpleDateFormat isoFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date expectedCreatedAt = isoFormatter.parse("2023-12-17 18:47:20");
        assertEquals(expectedCreatedAt, paymentResumeDTO.getCreatedAt());
    }
}
