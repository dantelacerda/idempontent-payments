package com.payments.service;

import com.payments.dto.CardPaymentDto;
import com.payments.dto.PaymentParametersDTO;
import com.payments.dto.PaymentResumeDTO;
import com.payments.repository.PaymentsRepository;
import com.payments.util.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class PaymentsServiceTest {

    @Mock
    private PaymentsRepository paymentsRepository;

    @InjectMocks
    private PaymentsService paymentsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void processBatchPayments_ValidPayments_ReturnsProcessedCount() {

        CardPaymentDto payment = new CardPaymentDto();
        payment.setPaymentId("123");
        payment.setPaymentType(Constants.CREDIT_CARD_PAYMENT_TYPE);
        when(paymentsRepository.listAllProcessedPayments()).thenReturn(Collections.emptySet());
        when(paymentsRepository.listAllPayments()).thenReturn(Collections.emptySet());

        // Act
        int processedCount = paymentsService.processBatchPayments(Constants.CREDIT_CARD_PAYMENT_TYPE, Arrays.asList(payment));

        // Assert
        assertEquals(1, processedCount);
    }

    @Test
    void updatePayment_CardPayment_ReturnsUpdatedResume() {
        // Arrange
        String paymentId = "12345";
        String status = "COMPLETED";
        CardPaymentDto cardPayment = new CardPaymentDto();
        cardPayment.setPaymentId(paymentId);
        Set<PaymentParametersDTO> payments = Collections.singleton(cardPayment);
        when(paymentsRepository.listAllPayments()).thenReturn(payments);

        paymentsService.processBatchPayments(Constants.CREDIT_CARD_PAYMENT_TYPE, Arrays.asList(cardPayment));
        // Act
        PaymentResumeDTO updatedResume = paymentsService.updatePayment(paymentId, status);

        // Assert
        assertNotNull(updatedResume);
        assertEquals(status, cardPayment.getStatus());
    }

    @Test
    void handleError_InvalidPayments_ReturnsBadRequest() {
        // Arrange
        List<PaymentParametersDTO> invalidPayments = Collections.singletonList(new CardPaymentDto());

        // Act
        ResponseEntity<String> response = paymentsService.handleError(invalidPayments);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().contains("Your payment(s) couldn't be processed. You are missing the PaymentIds."));
    }

    @Test
    void fetchPaymentListByType_ReturnsFilteredPayments() {
        // Arrange
        Set<PaymentParametersDTO> payments = Collections.singleton(new CardPaymentDto());
        when(paymentsRepository.listAllPayments()).thenReturn(payments);

        // Act
        CardPaymentDto cardPayment = new CardPaymentDto();
        cardPayment.setPaymentId("12345678");
        paymentsService.processBatchPayments(Constants.CREDIT_CARD_PAYMENT_TYPE, Arrays.asList(cardPayment));
        List<PaymentParametersDTO> filteredPayments = paymentsService.fetchPaymentListByType(Constants.CREDIT_CARD_PAYMENT_TYPE);

        // Assert
        assertEquals(2, filteredPayments.size());
    }

    // Add more tests as needed to cover other scenarios and methods in PaymentsService
}

