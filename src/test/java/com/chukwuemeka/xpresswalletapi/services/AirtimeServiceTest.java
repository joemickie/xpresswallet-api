package com.chukwuemeka.xpresswalletapi.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.chukwuemeka.xpresswalletapi.enums.TransactionStatus;
import com.chukwuemeka.xpresswalletapi.payloads.requests.AirtimeRequest;
import com.chukwuemeka.xpresswalletapi.payloads.requests.AirtimeRequestDetails;
import com.chukwuemeka.xpresswalletapi.payloads.responses.ApiResponse;
import com.chukwuemeka.xpresswalletapi.services.provider.XpressPayService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {AirtimeService.class})
@ExtendWith(SpringExtension.class)
class AirtimeServiceTest {
    @Autowired
    private AirtimeService airtimeService;


    @MockBean
    private XpressPayService xpressPayService;

    private AirtimeRequest request;


    @BeforeEach
    void setUp() {
        request = AirtimeRequest.builder()
                .phoneNumber("08033333333")
                .amount(10)
                .service("service")
                .build();
    }

    @Test
    void testBuyAirtimeFailed() {
        when(xpressPayService.buyAirtime(Mockito.<AirtimeRequestDetails>any())).thenReturn(TransactionStatus.PENDING);

        ApiResponse<String> actualBuyAirtimeResult = airtimeService.buyAirtime(request);

        verify(xpressPayService).buyAirtime(Mockito.<AirtimeRequestDetails>any());
        assertEquals("Transaction failed", actualBuyAirtimeResult.getMessage());
        assertNull(actualBuyAirtimeResult.getData());
        assertEquals(200, actualBuyAirtimeResult.getStatusCode().intValue());
        assertEquals(HttpStatus.OK, actualBuyAirtimeResult.getStatus());
    }

    @Test
    void testBuyAirtimeSuccess() {
        when(xpressPayService.buyAirtime(Mockito.<AirtimeRequestDetails>any())).thenReturn(TransactionStatus.SUCCESSFUL);

        ApiResponse<String> actualBuyAirtimeResult = airtimeService.buyAirtime(request);

        verify(xpressPayService).buyAirtime(Mockito.<AirtimeRequestDetails>any());
        assertEquals("Transaction successful", actualBuyAirtimeResult.getMessage());
        assertNull(actualBuyAirtimeResult.getData());
        assertEquals(200, actualBuyAirtimeResult.getStatusCode().intValue());
        assertEquals(HttpStatus.OK, actualBuyAirtimeResult.getStatus());
    }
}
