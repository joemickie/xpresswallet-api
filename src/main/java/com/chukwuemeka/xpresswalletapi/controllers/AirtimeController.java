package com.chukwuemeka.xpresswalletapi.controllers;

import com.chukwuemeka.xpresswalletapi.payloads.requests.AirtimeRequest;
import com.chukwuemeka.xpresswalletapi.payloads.responses.ApiResponse;
import com.chukwuemeka.xpresswalletapi.services.AirtimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/airtime")
public class AirtimeController {
    private final AirtimeService airtimeService;

    @PostMapping("/buy")
    public ResponseEntity<ApiResponse<String>> test(@RequestBody AirtimeRequest request) {
        var response = airtimeService.buyAirtime(request);
        return new ResponseEntity<>(response, response.getStatus());
    }
}
