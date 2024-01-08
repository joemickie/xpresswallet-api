package com.chukwuemeka.xpresswalletapi.payloads.requests;

import lombok.Data;

@Data
public class XpressAirtimeRequest {
    private String requestId;
    private String uniqueCode;
    private AirtimeRequestDetails details;
}