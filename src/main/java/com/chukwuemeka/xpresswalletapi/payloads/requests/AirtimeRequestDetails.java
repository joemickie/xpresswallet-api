package com.chukwuemeka.xpresswalletapi.payloads.requests;

import lombok.Data;

@Data
public class AirtimeRequestDetails {
    private String phoneNumber;
    private Integer amount;
}
