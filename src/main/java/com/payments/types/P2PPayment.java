package com.payments.types;

import com.payments.dto.P2PDto;

public class P2PPayment implements PaymentsInterface {
    private P2PDto p2PDto;

    public P2PPayment(P2PDto p2PDto) {
        this.p2PDto = p2PDto;
    }

    @Override
    public void processPayment() {
        // Implement credit card payment processing logic
        System.out.println("Processing P2P payment with status: " + p2PDto.getStatus());
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