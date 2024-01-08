package com.chukwuemeka.xpresswalletapi.services;

import com.chukwuemeka.xpresswalletapi.enums.TransactionStatus;
import com.chukwuemeka.xpresswalletapi.payloads.requests.AirtimeRequest;
import com.chukwuemeka.xpresswalletapi.payloads.requests.AirtimeRequestDetails;
import com.chukwuemeka.xpresswalletapi.payloads.responses.ApiResponse;
import com.chukwuemeka.xpresswalletapi.services.provider.XpressPayService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AirtimeService {
    private final XpressPayService payService;

    public ApiResponse<String> buyAirtime(AirtimeRequest request) {
        AirtimeRequestDetails details = new AirtimeRequestDetails();
        details.setPhoneNumber(request.getPhoneNumber());
        details.setAmount(request.getAmount());

        var response = payService.buyAirtime(details);
        if(response == TransactionStatus.SUCCESSFUL) {
            return new ApiResponse<>("Transaction successful", HttpStatus.OK);
        } else {
            return new ApiResponse<>("Transaction failed", HttpStatus.OK);
        }
    }
}
