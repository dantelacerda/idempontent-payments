package com.payments.controller;

import com.payments.dto.PaymentParametersDTO;
import com.payments.dto.PaymentResumeDTO;
import com.payments.service.PaymentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Set;


@RestController
@RequestMapping("/payments")
@Validated
public class PaymentsController {

    @Autowired
    private PaymentsService paymentsService;

    @PutMapping("/update_payment_status/{id}")
    public PaymentResumeDTO updateResource(@PathVariable String id, @RequestBody String status) {
        return paymentsService.updatePayment(id, status);
    }

    @GetMapping("/list_full")
    public ResponseEntity<Set<PaymentParametersDTO>> getAllPayments() {
        Set<PaymentParametersDTO> payments = paymentsService.fetchAllPayments();
        return ResponseEntity.ok(payments);
    }

    @GetMapping("/list_resume")
    public ResponseEntity<List<PaymentResumeDTO>> getAllPaymentsResume() {
        List<PaymentResumeDTO> payments = paymentsService.fetchPaymentsResume();
        return ResponseEntity.ok(payments);
    }

    @GetMapping("/list_flux")
    public Flux<PaymentParametersDTO> getAllPaymentsFlux() {
        Set<PaymentParametersDTO> payments = paymentsService.fetchAllPayments();
        return Flux.fromIterable(payments);
    }

}
