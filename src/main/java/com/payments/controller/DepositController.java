package com.payments.controller;

import com.payments.dto.DepositDto;
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
@RequestMapping("/deposit")
@Validated
public class DepositController {

    @Autowired
    private PaymentsService paymentsService;

    @PostMapping("/pay")
    public ResponseEntity<String> processPayment(@Valid @RequestBody DepositDto paymentRequest) {

        if (paymentsService.validateList(Arrays.asList(paymentRequest))) {
            paymentsService.processBatchPayments(Constants.DEPOSIT_PAYMENT_TYPE, Arrays.asList(paymentRequest));
            return ResponseEntity.ok("Payment processed successfully.");

        } else {
            return paymentsService.handleError(Arrays.asList(paymentRequest));
        }

    }

    @PostMapping("/batch_payment")
    public ResponseEntity<String> processListPayment(@RequestBody List<DepositDto> paymentsRequest) {

        List<PaymentParametersDTO> convertedList = (List<PaymentParametersDTO>) (List<?>) paymentsRequest;

        if (paymentsService.validateList(convertedList)) {
            int processed = paymentsService.processBatchPayments(Constants.DEPOSIT_PAYMENT_TYPE, convertedList);

            return ResponseEntity.ok("You sent " + convertedList.size() + " payment(s). " + processed +
                    " Of those might be processed. " + (convertedList.size() - processed) + " were already processed or contain errors.");

        } else {
            return paymentsService.handleError(convertedList);
        }

    }

    @GetMapping("/list")
    public ResponseEntity<List<PaymentParametersDTO>> getAllPayments() {
        List<PaymentParametersDTO> payments = paymentsService.fetchPaymentListByType(Constants.DEPOSIT_PAYMENT_TYPE);
        return ResponseEntity.ok(payments);
    }

    @GetMapping("/list_flux")
    public Flux<PaymentParametersDTO> getPaymentsFlux() {
        List<PaymentParametersDTO> payments = paymentsService.fetchPaymentListByType(Constants.DEPOSIT_PAYMENT_TYPE);
        return Flux.fromIterable(payments);
    }

}
