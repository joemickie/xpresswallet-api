package com.chukwuemeka.xpresswalletapi.services;

import com.chukwuemeka.xpresswalletapi.payloads.requests.LoginRequest;
import com.chukwuemeka.xpresswalletapi.payloads.requests.SignupRequest;
import com.chukwuemeka.xpresswalletapi.payloads.responses.ApiResponse;
import com.chukwuemeka.xpresswalletapi.payloads.responses.LoginResponse;
import com.chukwuemeka.xpresswalletapi.payloads.responses.SignupResponse;
import org.springframework.http.ResponseEntity;

/**
 * This is the Authentication service which handles all user login and signup
 */
public interface AuthService {
    /**
     * This handles user signup to the XpressApplication
     * @param signupRequest Signup payload which is needed to authenticate a user
     * @return ResponseEntity - ApiResponse - SignupResponse
     */
    ResponseEntity<ApiResponse<SignupResponse>> signup(SignupRequest signupRequest);

    /**
     * This handles user login authentication in the XpressApplication
     * @param loginRequest Login payload which is needed to verify an existing user
     * @return ResponseEntity - ApiResponse - SignupResponse
     */
    ResponseEntity<ApiResponse<LoginResponse>> login(LoginRequest loginRequest);
}
