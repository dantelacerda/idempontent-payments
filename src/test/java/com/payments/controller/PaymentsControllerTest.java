package com.payments.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.payments.dto.CardPaymentDto;
import com.payments.dto.PaymentParametersDTO;
import com.payments.dto.PaymentResumeDTO;
import com.payments.service.PaymentsService;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PaymentsControllerTest {

    @Mock
    private PaymentsService paymentsService;

    @InjectMocks
    private PaymentsController paymentController;

    private MockMvc mockMvc;

    private PaymentResumeDTO mockedPaymentResume() {
        String paymentId = "123";
        String status = "COMPLETED";
        CardPaymentDto payment = new CardPaymentDto();
        payment.setPaymentId(paymentId);
        payment.setStatus(status);
        PaymentResumeDTO mockPaymentResume = new PaymentResumeDTO(payment);

        return mockPaymentResume;
    }
    @Test
    public void testUpdateResource() throws Exception {
        String paymentId = "123";
        String status = "COMPLETED";

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(paymentController).build();

        when(paymentsService.updatePayment(eq(paymentId), eq(status))).thenReturn(mockedPaymentResume());

        mockMvc.perform(MockMvcRequestBuilders.put("/payments/update_payment_status/{id}", paymentId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(status))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(new ObjectMapper().writeValueAsString(mockedPaymentResume())));

        verify(paymentsService, times(1)).updatePayment(eq(paymentId), eq(status));
    }

    @Test
    public void testGetAllPayments() throws Exception {
        Set<PaymentParametersDTO> mockPayments = mock(HashSet.class);

        try {
            when(paymentsService.fetchAllPayments()).thenReturn(mockPayments);

            MockMvc mockMvc = MockMvcBuilders.standaloneSetup(paymentController).build();

            mockMvc.perform(MockMvcRequestBuilders.get("/payments/list_full"))
                    .andExpect(MockMvcResultMatchers.status().is5xxServerError());

            verify(paymentsService, times(1)).fetchAllPayments();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        verify(paymentsService, times(1)).fetchAllPayments();
    }

    @Test
    public void testGetAllPaymentsResume() throws Exception {
        List<PaymentResumeDTO> mockPaymentsResume = Arrays.asList(/* Initialize with test data */);

        when(paymentsService.fetchPaymentsResume()).thenReturn(mockPaymentsResume);
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(paymentController).build();

        mockMvc.perform(MockMvcRequestBuilders.get("/payments/list_resume"))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(paymentsService, times(1)).fetchPaymentsResume();
    }

    @Test
    public void testGetAllPaymentsFlux() throws Exception {
        List<PaymentResumeDTO> mockPayments  = mock(List.class);

        when(paymentsService.fetchPaymentsResume()).thenReturn(mockPayments);
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(paymentController).build();
        mockMvc.perform(MockMvcRequestBuilders.get("/payments/list_flux"))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(paymentsService, times(1)).fetchPaymentsResume();
    }
}


