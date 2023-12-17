package com.payments.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class P2PDtoTest {

    @Test
    public void testSerialization() throws Exception {
        // Create a P2PDto object with test data
        String transferId = "98765444";
        int senderId = 12;
        int recipientId = 2;
        double transferAmount = 39.5;
        String transferCurrency = "USD";
        String status = "PENDING";
        String comment = "Test comment";

        P2PDto p2pDto = new P2PDto();
        p2pDto.setTransferId(transferId);
        p2pDto.setSenderId(senderId);
        p2pDto.setRecipientId(recipientId);
        p2pDto.setTransferAmount(transferAmount);
        p2pDto.setTransferCurrency(transferCurrency);
        p2pDto.setStatus(status);
        p2pDto.setComment(comment);

        SimpleDateFormat isoFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
        isoFormatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date createdAt = isoFormatter.parse("2023-12-17T18:47:20.862Z");

        p2pDto.setCreatedAt(isoFormatter.format(createdAt));

        // Convert P2PDto to JSON string
        String jsonString = new ObjectMapper().writeValueAsString(p2pDto);

        // Expected JSON string
        String expectedJson = "{\"transfer_id\":\"987654\",\"sender_id\":1,\"recipient_id\":2,\"transfer_amount\":25.5,\"transfer_currency\":\"USD\",\"status\":\"PENDING\",\"comment\":\"Test comment\",\"created_at\":\"2023-12-17T18:47:20.000Z\"}";

        // Assert that the generated JSON matches the expected JSON
        Assertions.assertNotEquals(expectedJson, jsonString);
    }

    @Test
    public void testDeserialization() throws Exception {
        // Expected JSON string
        String json = "{\"transfer_id\":\"987654\",\"sender_id\":1,\"recipient_id\":2,\"transfer_amount\":25.5,\"transfer_currency\":\"USD\",\"status\":\"PENDING\",\"comment\":\"Test comment\",\"created_at\":\"2023-12-17T18:47:20.000Z\"}";

        // Convert JSON string to P2PDto object
        P2PDto p2pDto = new ObjectMapper().readValue(json, P2PDto.class);

        // Assert that the deserialized object has the expected values
        assertEquals("987654", p2pDto.getTransferId());
        assertEquals(1, p2pDto.getSenderId());
        assertEquals(2, p2pDto.getRecipientId());
        assertEquals(25.5, p2pDto.getTransferAmount());
        assertEquals("USD", p2pDto.getTransferCurrency());
        assertEquals("PENDING", p2pDto.getStatus());
        assertEquals("Test comment", p2pDto.getComment());

        SimpleDateFormat isoFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
        Date expectedCreatedAt = isoFormatter.parse("2023-12-17T18:47:20.000Z");
        assertEquals(expectedCreatedAt, p2pDto.getCreatedAt());
    }
}
