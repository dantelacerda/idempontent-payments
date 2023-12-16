package com.payments.types;

import com.payments.dto.CardPaymentDto;
import com.payments.dto.DepositDto;
import com.payments.dto.P2PDto;

public class PaymentStrategyFactory {
    public static PaymentStrategy createCreditCardPaymentStrategy(CardPaymentDto cardPayment) {
        return new CreditCardPaymentStrategy(cardPayment);
    }

    public static PaymentStrategy createDepositPaymentStrategy(DepositDto deposit) {
        return new DepositPaymentStrategy(deposit);
    }

    public static PaymentStrategy createP2PPaymentStrategy(P2PDto p2p) {
        return new P2PPaymentStrategy(p2p);
    }

}
