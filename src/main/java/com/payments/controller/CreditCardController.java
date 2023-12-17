package com.payments.controller;

import com.payments.dto.CardPaymentDto;
import com.payments.dto.PaymentParametersDTO;
import com.payments.repository.PaymentsRepository;
import com.payments.service.PaymentsService;
import com.payments.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import javax.validation.Valid;
import java.util.*;


@RestController
@RequestMapping("/credit_card")
@Validated
public class CreditCardController {

    @Autowired
    private PaymentsService paymentsService;

    @PostMapping("/pay")
    public ResponseEntity<String> processPayment(@Valid @RequestBody CardPaymentDto paymentRequest) {

        if (paymentsService.validateList(Arrays.asList(paymentRequest))) {
            paymentsService.processBatchPayments(Constants.CREDIT_CARD_PAYMENT_TYPE, Arrays.asList(paymentRequest));
            return ResponseEntity.ok("Payment processed successfully.");

        } else {
            return paymentsService.handleError(Arrays.asList(paymentRequest));
        }

    }

    @PostMapping("/batch_payment")
    public ResponseEntity<String> processListPayment(@RequestBody List<CardPaymentDto> paymentsRequest) {

        List<PaymentParametersDTO> convertedList = (List<PaymentParametersDTO>) (List<?>) paymentsRequest;

        if (paymentsService.validateList(convertedList)) {
            int processed = paymentsService.processBatchPayments(Constants.CREDIT_CARD_PAYMENT_TYPE, convertedList);

            return ResponseEntity.ok("You sent " + convertedList.size() + " payment(s). " + processed +
                    " Of those might be processed. " + (convertedList.size() - processed) + " were already processed or contain errors.");

        } else {
            return paymentsService.handleError(convertedList);
        }

    }

    @GetMapping("/list")
    public ResponseEntity<Set<PaymentParametersDTO>> getAllPayments() {
        return ResponseEntity.ok(PaymentsRepository.getInstance().listAllPayments());
    }

    @GetMapping("/list_flux")
    public Flux<PaymentParametersDTO> getAllItems() {
        return Flux.fromIterable(PaymentsRepository.getInstance().listAllPayments());
    }

}
