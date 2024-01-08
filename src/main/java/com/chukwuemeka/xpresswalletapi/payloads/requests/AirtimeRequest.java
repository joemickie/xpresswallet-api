package com.chukwuemeka.xpresswalletapi.payloads.requests;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AirtimeRequest {
    private String phoneNumber;
    private Integer amount;
    private String service;
}
