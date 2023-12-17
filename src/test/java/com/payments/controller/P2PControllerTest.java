package com.payments.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.payments.dto.CardPaymentDto;
import com.payments.dto.PaymentParametersDTO;
import com.payments.service.PaymentsService;
import com.payments.util.Constants;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class P2PControllerTest {

    @Mock
    private PaymentsService paymentsService;

    @InjectMocks
    private P2PController p2pController;

    @Test
    public void testProcessPayment() throws Exception {

        PaymentParametersDTO payment = new PaymentParametersDTO();
        payment.setPaymentId("1234");
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonPayload = objectMapper.writeValueAsString(payment);

        when(paymentsService.validateList(anyList())).thenReturn(true);
        when(paymentsService.processBatchPayments(eq(Constants.P2P_PAYMENT_TYPE), anyList())).thenReturn(1);

        p2pController = new P2PController();
        p2pController.paymentsService = paymentsService;

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(p2pController).build();

        mockMvc.perform(MockMvcRequestBuilders.post("/p2p/pay")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonPayload))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Payment processed successfully."));


        verify(paymentsService, times(1)).validateList(anyList());
        verify(paymentsService, times(1)).processBatchPayments(eq(Constants.P2P_PAYMENT_TYPE), anyList());
    }

    @Test
    public void testProcessListPayment() throws Exception {
        List<CardPaymentDto> paymentsRequest = Arrays.asList(new CardPaymentDto(/* Initialize with test data */));

        PaymentParametersDTO payment = new PaymentParametersDTO();
        payment.setPaymentId("1234");
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonPayload = objectMapper.writeValueAsString(Arrays.asList(payment));

        when(paymentsService.validateList(anyList())).thenReturn(true);
        when(paymentsService.processBatchPayments(eq(Constants.P2P_PAYMENT_TYPE), anyList())).thenReturn(1);

        p2pController = new P2PController();
        p2pController.paymentsService = paymentsService;

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(p2pController).build();

        mockMvc.perform(MockMvcRequestBuilders.post("/p2p/batch_payment")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonPayload))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("You sent 1 payment(s). 1 Of those might be processed. 0 were already processed or contain errors."));

        verify(paymentsService, times(1)).validateList(anyList());
        verify(paymentsService, times(1)).processBatchPayments(eq(Constants.P2P_PAYMENT_TYPE), anyList());
    }

    @Test
    public void testGetAllPayments() throws Exception {
        List<PaymentParametersDTO> mockPayments = Collections.singletonList(new PaymentParametersDTO(/* Initialize with test data */));

        when(paymentsService.fetchPaymentListByType(eq(Constants.P2P_PAYMENT_TYPE))).thenReturn(mockPayments);

        p2pController = new P2PController();
        p2pController.paymentsService = paymentsService;

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(p2pController).build();

        mockMvc.perform(MockMvcRequestBuilders.get("/p2p/list"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("[{ /* JSON representation of your PaymentParametersDTO */ }]"));

        verify(paymentsService, times(1)).fetchPaymentListByType(eq(Constants.P2P_PAYMENT_TYPE));
    }

    @Test
    public void testGetPaymentsFlux() throws Exception {
        List<PaymentParametersDTO> mockPayments = Collections.singletonList(new PaymentParametersDTO(/* Initialize with test data */));

        when(paymentsService.fetchPaymentListByType(eq(Constants.P2P_PAYMENT_TYPE))).thenReturn(mockPayments);

        p2pController = new P2PController();
        p2pController.paymentsService = paymentsService;

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(p2pController).build();

        mockMvc.perform(MockMvcRequestBuilders.get("/p2p/list_flux"))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(paymentsService, times(1)).fetchPaymentListByType(eq(Constants.P2P_PAYMENT_TYPE));
    }
}

