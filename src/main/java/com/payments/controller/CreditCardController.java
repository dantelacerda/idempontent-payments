package com.payments.controller;

import com.payments.dto.CardPaymentDto;
import com.payments.dto.PaymentParametersDTO;
import com.payments.service.PaymentsService;
import com.payments.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;


@RestController
@RequestMapping("/credit_card")
@Validated
public class CreditCardController {

    @Autowired
    protected PaymentsService paymentsService;

    @PostMapping("/pay")
    public ResponseEntity<String> processPayment(@Valid @RequestHeader("Idempotency-Key") String idempotencyKey, @RequestBody CardPaymentDto paymentRequest) {

        if (paymentsService.hasBeenProcessed(idempotencyKey) || !paymentsService.validateList(Arrays.asList(paymentRequest))) {
            return paymentsService.handleError(Arrays.asList(paymentRequest), idempotencyKey);
        } else {
            paymentsService.processBatchPayments(Constants.CREDIT_CARD_PAYMENT_TYPE, Arrays.asList(paymentRequest), idempotencyKey);
            return ResponseEntity.ok("Payment processed successfully.");
        }

    }

    @PostMapping("/batch_payment")
    public ResponseEntity<String> processListPayment(@RequestHeader("Idempotency-Key") String idempotencyKey, @RequestBody List<CardPaymentDto> paymentsRequest) {

        List<PaymentParametersDTO> convertedList = (List<PaymentParametersDTO>) (List<?>) paymentsRequest;

        if (paymentsService.hasBeenProcessed(idempotencyKey) || !paymentsService.validateList(convertedList)) {
            return paymentsService.handleError(convertedList, idempotencyKey);
        } else {
            int processed = paymentsService.processBatchPayments(Constants.CREDIT_CARD_PAYMENT_TYPE, convertedList, idempotencyKey);

            return ResponseEntity.ok("You sent " + convertedList.size() + " payment(s). " + processed +
                    " Of those might be processed. " + (convertedList.size() - processed) + " were already processed or contain errors.");

        }

    }

    @GetMapping("/list")
    public ResponseEntity<List<PaymentParametersDTO>> getAllPayments() {
        List<PaymentParametersDTO> payments = paymentsService.fetchPaymentListByType(Constants.CREDIT_CARD_PAYMENT_TYPE);
        return ResponseEntity.ok(payments);
    }

    @GetMapping("/list_flux")
    public Flux<PaymentParametersDTO> getPaymentsFlux() {
        List<PaymentParametersDTO> payments = paymentsService.fetchPaymentListByType(Constants.CREDIT_CARD_PAYMENT_TYPE);
        return Flux.fromIterable(payments);
    }

}
