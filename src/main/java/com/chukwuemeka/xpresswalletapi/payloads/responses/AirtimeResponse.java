package com.chukwuemeka.xpresswalletapi.payloads.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AirtimeResponse {
    private String requestId;
    private String referenceId;
    private String responseCode;
    private String responseMessage;
    private AirtimeResponseData data;
}
