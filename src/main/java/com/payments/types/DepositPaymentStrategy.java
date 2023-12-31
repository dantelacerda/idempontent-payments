package com.payments.types;

import com.payments.dto.DepositDto;
import com.payments.repository.PaymentsRepository;
import com.payments.util.Constants;

public class DepositPaymentStrategy implements PaymentStrategy {
    private DepositDto depositDto;

    public DepositPaymentStrategy(DepositDto depositDto) {
        this.depositDto = depositDto;
    }

    @Override
    public void processPayment() {
        // Implement credit card payment processing logic
        System.out.println("Processing payment by deposit with id: " + depositDto.getDepositId());
        depositDto.setPaymentType(Constants.DEPOSIT_PAYMENT_TYPE);
        depositDto.setPaymentId(depositDto.getDepositId());
        PaymentsRepository.getInstance().processPayment(depositDto);
    }
}

//{
//        "deposit_id": "071d500bf9d74c72a963d77d2b9a0107",
//        "user_id": 113411,
//        "deposit_amount": 12.99,
//        "deposit_currency": "GBP",
//        "status": "PENDING",
//        "created_at": "2023-12-02 13:54:30",
//        "expires_at": "2023-12-04 13:54:30",
//        "payment_method_code": "BANK_TRANSFER",
//        }


