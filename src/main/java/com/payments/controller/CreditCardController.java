package com.payments.controller;

import com.payments.dto.CardPaymentDto;
import com.payments.model.PaymentRequest;
import com.payments.types.PaymentContext;
import com.payments.types.PaymentStrategyFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;


@RestController
@RequestMapping("/credit_card")
public class CreditCardController {

    //Simulated storage for processed payments
    private final Set<String> processedPayments = new HashSet<>();

    @PostMapping("/pay")
    public ResponseEntity<String> processPayment(@RequestHeader("Idempotency-Key") String idempotencyKey, @RequestBody CardPaymentDto paymentRequest) {


        try {
            if (processedPayments.contains(idempotencyKey)) {

                return ResponseEntity.ok("Payment already processed. Returning previous result.");
            }

            PaymentContext context = new PaymentContext();

            context.setPaymentStrategyFromFactory("credit_card", paymentRequest);
            context.processPayment();
            processedPayments.add(idempotencyKey);

        } catch (Exception ex) {
            String errorMessage = "An error occurred while processing the request.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        }

        return ResponseEntity.ok("Payment processed successfully.");
    }

    @GetMapping("/list")
    public ResponseEntity<Set<String>> getAllProducts() {
        // Return a ResponseEntity with the Set of products and HttpStatus.OK
        return ResponseEntity.ok(processedPayments);
    }
}
