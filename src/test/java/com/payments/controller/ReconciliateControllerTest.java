package com.payments.controller;

import com.payments.dto.PaymentParametersDTO;
import com.payments.service.ReconciliateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ReconciliateControllerTest {

    @Mock
    private ReconciliateService reconciliateService;

    @InjectMocks
    private ReconciliateController reconciliateController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(reconciliateController).build();
    }

    @Test
    public void testProcessPayment() throws Exception {
        // Mock your service response
        Set<PaymentParametersDTO> mockReconciledSets = new HashSet<>();
        when(reconciliateService.reconcileSets(any())).thenReturn(mockReconciledSets);

        String content = "1234|DEPOSIT|CANCELED";
        InputStream inputStream = new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8));
        // Prepare a sample file

        MockMultipartFile file = new MockMultipartFile("file", "test.csv", MediaType.TEXT_PLAIN_VALUE, inputStream);

        // Perform the request and validate the response
        mockMvc.perform(MockMvcRequestBuilders.multipart("/reconciliate/file").file(file))
                .andExpect(status().isOk());
    }
}

