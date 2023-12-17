package com.payments.types;

import com.payments.dto.CardPaymentDto;
import com.payments.dto.DepositDto;
import com.payments.dto.P2PDto;
import com.payments.dto.PaymentParametersDTO;
import com.payments.util.Constants;

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
        if (Constants.CREDIT_CARD_PAYMENT_TYPE.equalsIgnoreCase(strategyType)) {
            setPaymentStrategy(PaymentStrategyFactory.createCreditCardPaymentStrategy((CardPaymentDto) parameter));
        } else if (Constants.DEPOSIT_PAYMENT_TYPE.equalsIgnoreCase(strategyType)) {
            setPaymentStrategy(PaymentStrategyFactory.createDepositPaymentStrategy((DepositDto) parameter));
        } else if (Constants.P2P_PAYMENT_TYPE.equalsIgnoreCase(strategyType)) {
            setPaymentStrategy(PaymentStrategyFactory.createP2PPaymentStrategy((P2PDto) parameter));
        }
    }
}