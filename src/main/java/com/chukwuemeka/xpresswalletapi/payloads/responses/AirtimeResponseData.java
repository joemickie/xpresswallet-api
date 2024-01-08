package com.chukwuemeka.xpresswalletapi.payloads.responses;

import lombok.Data;

@Data
public class AirtimeResponseData {
    private String channel;
    private Integer amount;
    private String phoneNumber;
}
