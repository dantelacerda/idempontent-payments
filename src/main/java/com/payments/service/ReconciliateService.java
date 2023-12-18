package com.payments.service;

import com.payments.dto.PaymentParametersDTO;
import com.payments.repository.PaymentsRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ReconciliateService {

    public Set<PaymentParametersDTO> reconcileSets(MultipartFile file) throws IOException {

        Set<PaymentParametersDTO> paymentsFromFile = this.readObjectsFromFile(file);
        Set<PaymentParametersDTO> allPayments = PaymentsRepository.getInstance().listAllPayments();
        reconciliateFiles(paymentsFromFile, allPayments);
        return PaymentsRepository.getInstance().listAllPayments();
    }

    private Set<PaymentParametersDTO> readObjectsFromFile(MultipartFile file) throws IOException {
        Set<PaymentParametersDTO> objectSet = new HashSet<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Split values by '|'
                List<String> values = Arrays.asList(line.split("\\|"));

                PaymentParametersDTO object = this.createPaymentByFileLine(values);
                objectSet.add(object);

            }


        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return objectSet;
    }

    private PaymentParametersDTO createPaymentByFileLine(List<String> values) {

        //Here i could add full rules to check payment type, fetch all fields
        //But i just wanna create an Example
        PaymentParametersDTO payment = new PaymentParametersDTO();

        payment.setPaymentId(values.get(0));
        payment.setPaymentType(values.get(1));

        return payment;
    }

    private void reconciliateFiles(Set<PaymentParametersDTO> paymentsFromFile, Set<PaymentParametersDTO> allPayments) {

        List<String> paymentsIds = paymentsFromFile.stream()
                .map(PaymentParametersDTO::getPaymentId)
                .collect(Collectors.toList());

        for (PaymentParametersDTO savedPayment : allPayments) {

            //Removing from All Payments list in case its not on file
            //I know the reconcilliation has other rules but just done it to have something
            if (!paymentsIds.contains(savedPayment.getPaymentId())) {
                PaymentsRepository.getInstance().deletePayment(savedPayment);
            }
        }
    }


}