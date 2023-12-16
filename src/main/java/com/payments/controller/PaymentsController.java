package com.payments.controller;

import com.payments.model.PaymentRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;


@RestController
@RequestMapping("/payments")
public class PaymentsController {
    //     Simulated storage for processed payments
    private final Set<String> processedPayments = new HashSet<>();

    @PostMapping("/processPayment")
    public ResponseEntity<String> processPayment(@RequestHeader("Idempotency-Key") String idempotencyKey, @RequestBody PaymentRequest paymentRequest) {

        // Check if the payment request is idempotent
        if (processedPayments.contains(idempotencyKey)) {

            return ResponseEntity.ok("Payment already processed. Returning previous result.");
        }

        // Store the idempotency key to mark the request as processed
        processedPayments.add(idempotencyKey);

        return ResponseEntity.ok("Payment processed successfully.");
    }
}
