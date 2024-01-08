package com.chukwuemeka.xpresswalletapi.services.implementations;

import com.chukwuemeka.xpresswalletapi.payloads.requests.LoginRequest;
import com.chukwuemeka.xpresswalletapi.payloads.requests.SignupRequest;
import com.chukwuemeka.xpresswalletapi.entities.User;
import com.chukwuemeka.xpresswalletapi.setup.exceptions.XpressException;
import com.chukwuemeka.xpresswalletapi.payloads.responses.ApiResponse;
import com.chukwuemeka.xpresswalletapi.payloads.responses.LoginResponse;
import com.chukwuemeka.xpresswalletapi.payloads.responses.SignupResponse;
import com.chukwuemeka.xpresswalletapi.repositories.UserRepository;
import com.chukwuemeka.xpresswalletapi.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthImplementation implements AuthService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtImplementation jwtImplementation;
    private final PasswordEncoder passwordEncoder;

    protected String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("first_name", user.getFirstName());
        claims.put("last_name", user.getLastName());
        return jwtImplementation.generateJwtToken(claims, user.getEmailAddress());
    }

    @Override
    public ResponseEntity<ApiResponse<SignupResponse>> signup(SignupRequest signupRequest) {
        var user = userRepository.findByEmailAddress(signupRequest.emailAddress());
        if(user.isPresent()) {
            throw new XpressException("Email address already exists");
        } else {
            User newUser = new User();
            newUser.setEmailAddress(signupRequest.emailAddress());
            newUser.setEncryptedPassword(passwordEncoder.encode(signupRequest.password()));
            newUser.setFirstName(signupRequest.firstName());
            newUser.setLastName(signupRequest.lastName());
            newUser.setPhoneNumber(signupRequest.phoneNumber());

            userRepository.save(newUser);
            var response = new SignupResponse(signupRequest.emailAddress(), signupRequest.firstName(), signupRequest.lastName());
            ApiResponse<SignupResponse> apiResponse = new ApiResponse<>(response);
            return new ResponseEntity<>(apiResponse, apiResponse.getStatus());
        }
    }

    @Override
    public ResponseEntity<ApiResponse<LoginResponse>> login(LoginRequest loginRequest) {
        LoginResponse loginResponse = new LoginResponse();

        userRepository.findByEmailAddress(loginRequest.emailAddress())
                .ifPresentOrElse(user -> {
                    Authentication authentication = authenticationManager.authenticate(
                            new UsernamePasswordAuthenticationToken(loginRequest.emailAddress(), loginRequest.password())
                    );
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    loginResponse.setToken(generateToken(user));
                    loginResponse.setEmailAddress(user.getEmailAddress());
                    loginResponse.setFirstName(user.getFirstName());
                    loginResponse.setLastName(user.getLastName());
                }, () -> {
                    throw new UsernameNotFoundException("User not found");
                });
        ApiResponse<LoginResponse> response = new ApiResponse<>(loginResponse);
        return new ResponseEntity<>(response, response.getStatus());
    }
}
