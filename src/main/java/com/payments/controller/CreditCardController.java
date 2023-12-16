package com.payments.controller;

import com.payments.dto.CardPaymentDto;
import com.payments.types.PaymentContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.List;
import java.util.stream.Collectors;
import reactor.core.publisher.Flux;


@RestController
@RequestMapping("/credit_card")
@Validated
public class CreditCardController {

    //Simulated storage for processed payments
    private final Set<String> processedPaymentsIds = new HashSet<>();
    private final Set<CardPaymentDto> processedPayments = new HashSet<>();

    @PostMapping("/pay")
    public ResponseEntity<String> processPayment(@Valid @RequestBody CardPaymentDto paymentRequest) {

        if(!isValidPayment(paymentRequest)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Verify fields");
        }

        try {
            //Assuming the paymentId is the idempontentKey
            if (processedPaymentsIds.contains(paymentRequest.getPaymentId())) {

                return ResponseEntity.ok("Payment already processed. Returning previous result.");
            }

            PaymentContext context = new PaymentContext();

            context.setPaymentStrategyFromFactory("credit_card", paymentRequest);
            context.processPayment();
            processedPaymentsIds.add(paymentRequest.getPaymentId());
            processedPayments.add(paymentRequest);

        } catch (Exception ex) {
            String errorMessage = "An error occurred while processing the request.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        }

        return ResponseEntity.ok("Payment processed successfully.");
    }

    @PostMapping("/pay_list")
    public ResponseEntity<String> processListPayment(@RequestBody List<CardPaymentDto> paymentsRequest) {

        int canProccess=0;
        int totalPayments=paymentsRequest.size();

        try {

            PaymentContext context = new PaymentContext();

            // Use Java Streams to filter objects based on specificField
            List<CardPaymentDto> filteredObjects = paymentsRequest.stream()
                    .filter(obj -> (!processedPaymentsIds.contains(obj.getPaymentId())) && isValidPayment(obj))
                    .collect(Collectors.toList());

            // Iterate over the filtered objects


            canProccess = filteredObjects.size();
            filteredObjects.forEach(obj -> {
                context.setPaymentStrategyFromFactory("credit_card", obj);
                context.processPayment();
                processedPaymentsIds.add(obj.getPaymentId());
                processedPayments.add(obj);
            });


        } catch (Exception ex) {
            String errorMessage = "An error occurred while processing the request.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        }

        return ResponseEntity.ok("You sent " +totalPayments + " payment(s). "+canProccess+
                " Of those might be processed. "+(totalPayments-canProccess)+ " were already processed or contain errors.");
    }

    @GetMapping("/list")
    public ResponseEntity<Set<CardPaymentDto>> getAllPayments() {
        return ResponseEntity.ok(processedPayments);
    }

    @GetMapping("/pay_list_flux")
    public Flux<CardPaymentDto> getAllItems() {
        return Flux.fromIterable(processedPayments);
    }

    private boolean isValidPayment(CardPaymentDto payment) {
        return !Objects.requireNonNullElse(payment.getPaymentId(), "").isEmpty();
    }
}
