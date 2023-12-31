package com.payments.repository;

import com.payments.dto.PaymentParametersDTO;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class PaymentsRepository {

    private static final PaymentsRepository instance = new PaymentsRepository();
    private final Set<PaymentParametersDTO> paymentsList;
    private final Set<String> processedPaymentsIds;
    private final Set<String> idempontencyIds;

    private PaymentsRepository() {
        this.paymentsList = new HashSet<>();
        this.processedPaymentsIds = new HashSet<>();
        this.idempontencyIds = new HashSet<>();
    }

    public static PaymentsRepository getInstance() {
        return instance;
    }

    public Set<PaymentParametersDTO> listAllPayments() {
        return Collections.unmodifiableSet(paymentsList);
    }
    public Set<String> listAllProcessedPayments() {
        return Collections.unmodifiableSet(processedPaymentsIds);
    }

    public void processPayment(PaymentParametersDTO item) {
        paymentsList.add(item);
        processedPaymentsIds.add(item.getPaymentId());
    }

    public void addIdempontentId(String idempontentId) {
        idempontencyIds.add(idempontentId);
    }
    public Set<String> listExecutedOperations() {
        return Collections.unmodifiableSet(idempontencyIds);
    }


    public void deletePayment(PaymentParametersDTO item) {
        paymentsList.remove(item);
    }

}
