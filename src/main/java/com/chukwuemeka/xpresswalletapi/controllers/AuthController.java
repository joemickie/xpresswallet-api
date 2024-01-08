package com.chukwuemeka.xpresswalletapi.controllers;

import com.chukwuemeka.xpresswalletapi.payloads.requests.LoginRequest;
import com.chukwuemeka.xpresswalletapi.payloads.requests.SignupRequest;
import com.chukwuemeka.xpresswalletapi.payloads.responses.ApiResponse;
import com.chukwuemeka.xpresswalletapi.payloads.responses.LoginResponse;
import com.chukwuemeka.xpresswalletapi.payloads.responses.SignupResponse;
import com.chukwuemeka.xpresswalletapi.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<SignupResponse>> signup(@RequestBody SignupRequest signup) {
        return authService.signup(signup);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@RequestBody LoginRequest login) {
        return authService.login(login);
    }
}
