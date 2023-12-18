package com.payments.controller;

import com.payments.dto.PaymentParametersDTO;
import com.payments.service.ReconciliateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Set;

@RestController
@RequestMapping("/reconciliate")
public class ReconciliateController {

    @Autowired
    protected ReconciliateService reconciliateService;

    @PostMapping("/file")
    public ResponseEntity<Set<PaymentParametersDTO>> processPayment(@RequestParam("file") MultipartFile file) {
        try {

            Set<PaymentParametersDTO> reconcilliatedSets = reconciliateService.reconcileSets(file);

            return ResponseEntity.ok(reconcilliatedSets);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
