package com.payments.types;

import com.payments.dto.CardPaymentDto;

public class CreditCardPayment implements PaymentsInterface {
    private CardPaymentDto cardPayment;
    private String idempotencyKey;


    public CreditCardPayment(CardPaymentDto cardPayment) {
        this.cardPayment = cardPayment;
    }

    @Override
    public void processPayment() {
        // Implement credit card payment processing logic
        System.out.println("Processing craedit card payment with card number: " + cardPayment.getCardId());
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
