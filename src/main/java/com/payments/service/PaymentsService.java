package com.payments.service;

import com.payments.dto.PaymentParametersDTO;
import com.payments.repository.PaymentsRepository;
import com.payments.types.PaymentContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class PaymentsService {

    public int processBatchPayments(String paymentType, List<PaymentParametersDTO> payments) {

        List<PaymentParametersDTO> validPaymentsToProccess = this.fetchOnlyValidPaymentsToProcess(payments);
        PaymentContext context = new PaymentContext();
        int processedPayments = validPaymentsToProccess.size();

        validPaymentsToProccess.forEach(obj -> {
            context.setPaymentStrategyFromFactory(paymentType, obj);
            context.processPayment();
        });

        return processedPayments;
    }

    public ResponseEntity<String> handleError(List<PaymentParametersDTO> payments) {

        if(this.filterValidPayments(payments).isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Your payment(s) couldn't be processed. You are missing the PaymentIds.");
        } else if(this.filterOnlyNotProcessedPayments(payments).isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Payment(s) already processed. Check your status again in some minutes.");
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error");
    }

    public boolean validateList(List<PaymentParametersDTO> paymentList) {
        return (!this.filterValidPayments(paymentList).isEmpty() && !this.filterOnlyNotProcessedPayments(paymentList).isEmpty());
    }

    public List<PaymentParametersDTO> filterValidPayments (List<PaymentParametersDTO> payments) {
        List<PaymentParametersDTO> filteredObjectsWithId = payments.stream()
                .filter(obj -> isValidPayment(obj))
                .collect(Collectors.toList());

        return filteredObjectsWithId;
    }

    public boolean isValidPayment(PaymentParametersDTO payment) {

        return !Objects.requireNonNullElse(payment.getPaymentId(), "").isEmpty();
    }

    public List<PaymentParametersDTO> filterOnlyNotProcessedPayments(List<PaymentParametersDTO> payments){

        List<PaymentParametersDTO> filteredObjects = payments.stream()
                .filter(obj -> (!PaymentsRepository.getInstance().listAllProcessedPayments().contains(obj.getPaymentId())))
                .collect(Collectors.toList());

        return filteredObjects;
    }

    protected List<PaymentParametersDTO> fetchOnlyValidPaymentsToProcess(List<PaymentParametersDTO> allPayments) {
        return this.filterOnlyNotProcessedPayments(this.filterValidPayments(allPayments));
    }
}
