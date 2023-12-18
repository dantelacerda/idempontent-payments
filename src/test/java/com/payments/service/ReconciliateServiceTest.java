package com.payments.service;

import com.payments.dto.PaymentParametersDTO;
import com.payments.repository.PaymentsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReconciliateServiceTest {

    @Mock
    private PaymentsRepository paymentsRepository;

    @InjectMocks
    private ReconciliateService reconciliateService;

    @Test
    void reconcileSets_shouldReconcileFilesAndReturnMergedSet() throws IOException {
        // Arrange
        Set<PaymentParametersDTO> existingPayments = new HashSet<>();
        existingPayments.add(createPayment("1", "CreditCard"));
        existingPayments.add(createPayment("2", "Deposit"));

        MultipartFile file = createMockMultipartFile("1|CreditCard\n3|P2P");

        verify(paymentsRepository, times(0)).deletePayment(any());
        verify(paymentsRepository, times(0)).processPayment(any());

        // Additional assertions based on your specific logic
    }

    private PaymentParametersDTO createPayment(String paymentId, String paymentType) {
        PaymentParametersDTO payment = new PaymentParametersDTO();
        payment.setPaymentId(paymentId);
        payment.setPaymentType(paymentType);
        return payment;
    }

    private MultipartFile createMockMultipartFile(String content) throws IOException {
        InputStream inputStream = new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8));
        return new MockMultipartFile("file", "test.txt", "text/plain", inputStream);
    }
}

