package com.payments.types;

import com.payments.dto.CardPaymentDto;
import com.payments.dto.DepositDto;
import com.payments.dto.P2PDto;
import com.payments.dto.PaymentParametersDTO;

public class PaymentContext {
    private PaymentStrategy paymentStrategy;

    public void setPaymentStrategy(PaymentStrategy paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
    }

    public void processPayment() {
        if (paymentStrategy != null) {
            paymentStrategy.processPayment();
        } else {
            System.out.println("No payment strategy set.");
        }
    }

    public void setPaymentStrategyFromFactory(String strategyType, PaymentParametersDTO parameter) {
        if ("credit_card".equalsIgnoreCase(strategyType)) {
            setPaymentStrategy(PaymentStrategyFactory.createCreditCardPaymentStrategy((CardPaymentDto) parameter));
        } else if ("deposit".equalsIgnoreCase(strategyType)) {
            setPaymentStrategy(PaymentStrategyFactory.createDepositPaymentStrategy((DepositDto) parameter));
        } else if ("p2p".equalsIgnoreCase(strategyType)) {
            setPaymentStrategy(PaymentStrategyFactory.createP2PPaymentStrategy((P2PDto) parameter));
        }
    }
}