package com.payments.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CardPaymentDtoTest {

    @Test
    void deserializeJsonString_CorrectJson_CreatesCardPaymentDtoObject() throws IOException {
        // Arrange
        String jsonString = "{" +
                "\"payment_id\":\"123456\"," +
                "\"card_id\":789," +
                "\"user_id\":456," +
                "\"payment_amount\":10.0," +
                "\"payment_currency\":\"USD\"," +
                "\"status\":\"COMPLETED\"," +
                "\"created_at\":\"2023-12-17 18:47:20\"," +
                "\"merchant_name\":\"Example Merchant\"," +
                "\"merchant_id\":987," +
                "\"mcc_code\":1234" +
                "}";

        // Act
        CardPaymentDto cardPaymentDto = new ObjectMapper().readValue(jsonString, CardPaymentDto.class);

        // Assert
        assertNotNull(cardPaymentDto);
        assertEquals("123456", cardPaymentDto.getPaymentId());
        assertEquals(789, cardPaymentDto.getCardId());
        assertEquals(456, cardPaymentDto.getUserId());
        assertEquals(10.0, cardPaymentDto.getPaymentAmount());
        assertEquals("USD", cardPaymentDto.getPaymentCurrency());
        assertEquals("COMPLETED", cardPaymentDto.getStatus());

        SimpleDateFormat isoFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        isoFormatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        assertEquals("2023-12-17 18:47:20", isoFormatter.format(cardPaymentDto.getCreatedAt()));

        assertEquals("Example Merchant", cardPaymentDto.getMerchantName());
        assertEquals(987, cardPaymentDto.getMerchantId());
        assertEquals(1234, cardPaymentDto.getMccCode());
    }

    @Test
    void serializeToJsonString_CreatesCorrectJsonString() throws Exception {
        // Arrange
        CardPaymentDto cardPaymentDto = new CardPaymentDto();
        cardPaymentDto.setPaymentId("78903232112");
        cardPaymentDto.setCardId(456);
        cardPaymentDto.setUserId(789);
        cardPaymentDto.setPaymentAmount(15.0);
        cardPaymentDto.setPaymentCurrency("EUR");
        cardPaymentDto.setStatus("PENDING");

        SimpleDateFormat isoFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        isoFormatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date createdAt = isoFormatter.parse("2023-12-17 18:47:20");

        cardPaymentDto.setCreatedAt(isoFormatter.format(createdAt));

        cardPaymentDto.setMerchantName("New Merchant");
        cardPaymentDto.setMerchantId(654);
        cardPaymentDto.setMccCode(11);

        // Act
        String jsonString = new ObjectMapper().writeValueAsString(cardPaymentDto);

        // Assert
        Assertions.assertNotEquals("{\"payment_id\":\"789012\",\"card_id\":456,\"user_id\":789,\"payment_amount\":15.0,\"payment_currency\":\"EUR\",\"status\":\"PENDING\",\"created_at\":\"" + createdAt.toString() + "\",\"merchant_name\":\"New Merchant\",\"merchant_id\":654,\"mcc_code\":5678}", jsonString);
    }
}

