package com.payments.service;

import com.payments.dto.*;
import com.payments.repository.PaymentsRepository;
import com.payments.types.PaymentContext;
import com.payments.util.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;
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

    public List<PaymentParametersDTO> filterValidPayments(List<PaymentParametersDTO> payments) {
        List<PaymentParametersDTO> filteredObjectsWithId = payments.stream()
                .filter(obj -> isValidPayment(obj))
                .collect(Collectors.toList());

        return filteredObjectsWithId;
    }

    public boolean isValidPayment(PaymentParametersDTO payment) {

        String paymentId = (payment instanceof CardPaymentDto) ? payment.getPaymentId() :
                (payment instanceof DepositDto) ? ((DepositDto) payment).getDepositId() :
                        (payment instanceof P2PDto) ? ((P2PDto) payment).getTransferId() :
                                "";

        return !Objects.requireNonNullElse(paymentId, "").isEmpty();
    }

    public List<PaymentParametersDTO> filterOnlyNotProcessedPayments(List<PaymentParametersDTO> payments) {


        //Verify the type of Payment to lookup the correct UUID
        List<PaymentParametersDTO> filteredObjects = payments.stream()
                .filter(payment -> (!PaymentsRepository.getInstance().listAllProcessedPayments().contains(
                        (payment instanceof CardPaymentDto) ? payment.getPaymentId() :
                                (payment instanceof DepositDto) ? ((DepositDto) payment).getDepositId() :
                                        (payment instanceof P2PDto) ? ((P2PDto) payment).getTransferId() :
                                                "")))
                .collect(Collectors.toList());

        return filteredObjects;
    }

    protected List<PaymentParametersDTO> fetchOnlyValidPaymentsToProcess(List<PaymentParametersDTO> allPayments) {
        return this.filterOnlyNotProcessedPayments(this.filterValidPayments(allPayments));
    }

    public List<PaymentParametersDTO> fetchPaymentListByType(String paymentType) {
        Set<PaymentParametersDTO> payments = PaymentsRepository.getInstance().listAllPayments();

        List<PaymentParametersDTO> filteredByType = payments.stream()
                .filter(obj -> (
                        paymentType.equals(Constants.CREDIT_CARD_PAYMENT_TYPE) ? (obj instanceof CardPaymentDto) :
                                paymentType.equals(Constants.DEPOSIT_PAYMENT_TYPE) ? (obj instanceof DepositDto) :
                                        paymentType.equals(Constants.P2P_PAYMENT_TYPE) ? (obj instanceof P2PDto) :
                                                null
                ))
                .collect(Collectors.toList());
        return filteredByType;
    }

    public Set<PaymentParametersDTO> fetchAllPayments() {
        Set<PaymentParametersDTO> payments = PaymentsRepository.getInstance().listAllPayments();

        return payments;
    }

    public List<PaymentResumeDTO> fetchPaymentsResume() {
        Set<PaymentParametersDTO> payments = PaymentsRepository.getInstance().listAllPayments();
        List<PaymentResumeDTO> paymentsResume = payments.stream()
                .map(originalObj -> new PaymentResumeDTO(originalObj))
                .collect(Collectors.toList());

        return paymentsResume;
    }

    public boolean validateList(List<PaymentParametersDTO> paymentList) {
        return (!this.filterValidPayments(paymentList).isEmpty() && !this.filterOnlyNotProcessedPayments(paymentList).isEmpty());
    }

    public PaymentResumeDTO updatePayment(String idPayment, String status) {

        Set<PaymentParametersDTO> payments = PaymentsRepository.getInstance().listAllPayments();
        PaymentResumeDTO resume = null;
        for (PaymentParametersDTO item : payments) {
            if (item instanceof CardPaymentDto) {
                CardPaymentDto card = (CardPaymentDto) item;
                if (card.getPaymentId().equals(idPayment)) {
                    card.setStatus(status);
                }
            } else if (item instanceof DepositDto) {
                DepositDto deposit = (DepositDto) item;
                if (deposit.getDepositId().equals(idPayment)) {
                    deposit.setStatus(status);
                }
            } else if (item instanceof P2PDto) {
                P2PDto p2p = (P2PDto) item;
                if (p2p.getTransferId().equals(idPayment)) {
                    p2p.setStatus(status);
                }
            }
            resume = new PaymentResumeDTO(item);
        }

        return resume;
    }

    public ResponseEntity<String> handleError(List<PaymentParametersDTO> payments) {

        if (this.filterValidPayments(payments).isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Your payment(s) couldn't be processed. You are missing the PaymentIds.");
        } else if (this.filterOnlyNotProcessedPayments(payments).isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Payment(s) already processed. Check your status again in some minutes.");
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error");
    }


}
