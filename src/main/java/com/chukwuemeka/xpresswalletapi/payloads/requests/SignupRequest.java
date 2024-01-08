package com.chukwuemeka.xpresswalletapi.payloads.requests;

import lombok.Builder;

@Builder
public record SignupRequest(
        String emailAddress,
        String firstName,
        String lastName,
        String phoneNumber,
        String password
) {
}
