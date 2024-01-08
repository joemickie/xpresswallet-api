package com.chukwuemeka.xpresswalletapi.services.provider;

import com.chukwuemeka.xpresswalletapi.enums.TransactionStatus;
import com.chukwuemeka.xpresswalletapi.payloads.requests.XpressAirtimeRequest;
import com.chukwuemeka.xpresswalletapi.payloads.requests.AirtimeRequestDetails;
import com.chukwuemeka.xpresswalletapi.payloads.responses.AirtimeResponse;
import com.chukwuemeka.xpresswalletapi.setup.exceptions.XpressException;
import com.chukwuemeka.xpresswalletapi.utils.PayUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class XpressPayService {
    private final RestTemplate rest;

    @Value("${XPRESS_PUBLIC_KEY}")
    private String PUBLIC_KEY;

    @Value("${XPRESS_PRIVATE_KEY}")
    private String PRIVATE_KEY;

    public HttpHeaders header(String hash) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer "+ PUBLIC_KEY);
        headers.set("PaymentHash", hash);
        headers.set("Channel", "API");
        return headers;
    }

    public TransactionStatus buyAirtime(AirtimeRequestDetails details) {
        String ENDPOINT = "https://billerstest.xpresspayments.com:9603/api/v1/airtime/fulfil";

        XpressAirtimeRequest request = new XpressAirtimeRequest();
        request.setRequestId(PayUtils.generateRequestId());
        request.setDetails(details);
        request.setUniqueCode("MTN_24207");

        String hash = PayUtils.calculateHMAC512(request, PRIVATE_KEY);
        HttpEntity<XpressAirtimeRequest> entity = new HttpEntity<>(request, header(hash));

        var response = rest.postForEntity(ENDPOINT, entity, AirtimeResponse.class);
        if(response.getStatusCode().is2xxSuccessful()) {
            var dataResponse = response.getBody();
            if(Objects.requireNonNull(dataResponse).getResponseMessage().equalsIgnoreCase("successful")) {
                if(!ObjectUtils.isEmpty(dataResponse.getData().getPhoneNumber())) {
                    return TransactionStatus.SUCCESSFUL;
                }
            } else {
                throw new XpressException("Transaction failed. Try again");
            }
        }
        throw new XpressException("An error occurred while handling request");
    }
}
