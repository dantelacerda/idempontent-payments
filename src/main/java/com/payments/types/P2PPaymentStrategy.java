package com.payments.types;

import com.payments.dto.P2PDto;
import com.payments.repository.PaymentsRepository;
import com.payments.util.Constants;

public class P2PPaymentStrategy implements PaymentStrategy {
    private P2PDto p2PDto;

    public P2PPaymentStrategy(P2PDto p2PDto) {
        this.p2PDto = p2PDto;
    }

    @Override
    public void processPayment() {
        // Implement credit card payment processing logic
        System.out.println("Processing P2P payment with id: " + p2PDto.getTransferId());
        p2PDto.setPaymentType(Constants.P2P_PAYMENT_TYPE);
        p2PDto.setPaymentId(p2PDto.getTransferId());
        PaymentsRepository.getInstance().processPayment(p2PDto);
    }
}


//{
//        "transfer_id": "7bec23e832ee41bfa0d59497ffccc553",
//        "sender_id":"113411",
//        "recipient_id":"113412",
//        "transfer_amount": 15,
//        "transfer_currency": "EUR",
//        "status":"COMPLETED",
//        "comment":"Dinner party at Richard’s",
//        "created_at": "2023-12-11 13:38:16"
//        }