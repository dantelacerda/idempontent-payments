package com.payments.repository;

import com.payments.dto.PaymentParametersDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PaymentsRepositoryTest {

    private PaymentsRepository paymentsRepository;

    @BeforeEach
    void setUp() {
        paymentsRepository = PaymentsRepository.getInstance();
    }

    @Test
    void processPayment_AddsPaymentToListAndProcessedIds() {
        // Arrange
        PaymentParametersDTO payment = mock(PaymentParametersDTO.class);
        when(payment.getPaymentId()).thenReturn("payment123");

        // Act
        paymentsRepository.processPayment(payment);

        // Assert
        Set<PaymentParametersDTO> paymentsList = paymentsRepository.listAllPayments();
        Set<String> processedIds = paymentsRepository.listAllProcessedPayments();

        assertEquals(1, paymentsList.size());
        assertEquals(1, processedIds.size());
        assertEquals(payment, paymentsList.iterator().next());
        Assertions.assertTrue(processedIds.contains("payment123"));
    }

    @Test
    void listAllPayments_ReturnsUnmodifiableSet() {
        // Arrange
        PaymentParametersDTO payment = mock(PaymentParametersDTO.class);
        paymentsRepository.processPayment(payment);

        // Act
        Set<PaymentParametersDTO> result = paymentsRepository.listAllPayments();

        // Assert
        Assertions.assertThrows(UnsupportedOperationException.class, () -> result.add(mock(PaymentParametersDTO.class)));
    }

    @Test
    void listAllProcessedPayments_ReturnsUnmodifiableSet() {
        // Arrange
        PaymentParametersDTO payment = mock(PaymentParametersDTO.class);
        paymentsRepository.processPayment(payment);

        // Act
        Set<String> result = paymentsRepository.listAllProcessedPayments();

        // Assert
        Assertions.assertThrows(UnsupportedOperationException.class, () -> result.add("payment789"));
    }
}
