package com.payments.types;

import com.payments.dto.CardPaymentDto;
import com.payments.repository.PaymentsRepository;
import com.payments.util.Constants;

public class CreditCardPaymentStrategy implements PaymentStrategy {
    private CardPaymentDto cardPayment;

    public CreditCardPaymentStrategy(CardPaymentDto cardPayment) {
        this.cardPayment = cardPayment;
    }

    @Override
    public void processPayment() {
        System.out.println("Processing credit card payment with id: " + cardPayment.getPaymentId());
        cardPayment.setPaymentType(Constants.CREDIT_CARD_PAYMENT_TYPE);
        PaymentsRepository.getInstance().processPayment(cardPayment);
    }

//    {
//        "payment_id": "8c051707aadd416d8e7e094971e395c0",
//            "card_id": 12341234,
//            "user_id": 113411,
//            "payment_amount": 10,
//            "payment_currency": "EUR",
//            "status": "COMPLETED",
//            "created_at": "2023-12-14 13:37:31",
//            "merchant_name": "TFL*LONDON",
//            "merchant_id": 12309,
//            "mcc_code": 7399
//    }
}
