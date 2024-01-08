package com.chukwuemeka.xpresswalletapi.services.implementations;

import com.chukwuemeka.xpresswalletapi.entities.User;
import com.chukwuemeka.xpresswalletapi.payloads.requests.LoginRequest;
import com.chukwuemeka.xpresswalletapi.payloads.requests.SignupRequest;
import com.chukwuemeka.xpresswalletapi.payloads.responses.ApiResponse;
import com.chukwuemeka.xpresswalletapi.payloads.responses.LoginResponse;
import com.chukwuemeka.xpresswalletapi.payloads.responses.SignupResponse;
import com.chukwuemeka.xpresswalletapi.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
@ContextConfiguration(classes = {AuthImplementation.class})
//@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
class AuthImplementationTest {
    @MockBean
    private UserRepository repository;
    @Autowired
    private AuthImplementation implementation;
    @MockBean
    private AuthenticationManager authenticationManager;
    @MockBean
    private JwtImplementation jwtImplementation;
    @MockBean
    private PasswordEncoder passwordEncoder;
    private User user;
    @BeforeEach
    void setUp() {
        user = User.builder()
                .emailAddress("example@gmail.com")
                .encryptedPassword("password")
                .firstName("firstname")
                .lastName("lastname")
                .phoneNumber("08033333333")
                .build();
    }

    @Test
    void generateToken() {
    }

    @Test
    void testSignup() {
        SignupRequest request  = new SignupRequest("example@gmail.com","firstname","lastname", "08033333333", "password");
        var apiResponse = new SignupResponse(request.emailAddress(),request.firstName(),request.lastName());
        when(repository.findByEmailAddress(request.emailAddress())).thenReturn(Optional.empty());

        ResponseEntity<ApiResponse<SignupResponse>> response = implementation.signup(request);
        assertThat(response.getBody()).isNotNull();
        assertThat(Objects.requireNonNull(response.getBody()).getData()).isEqualTo(apiResponse);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void testLogin() throws AuthenticationException {
        Optional<User> ofResult = Optional.of(user);
        when(repository.findByEmailAddress(Mockito.<String>any())).thenReturn(ofResult);
        when(jwtImplementation.generateJwtToken(Mockito.<Map<String, Object>>any(), Mockito.<String>any()))
                .thenReturn("ABC123");
        when(authenticationManager.authenticate(Mockito.<Authentication>any()))
                .thenReturn(new TestingAuthenticationToken("Principal", "Credentials"));

        // Act
        ResponseEntity<ApiResponse<LoginResponse>> actualLoginResult = implementation
                .login(new LoginRequest("example@gmail.com", "password"));

        // Assert
        verify(repository).findByEmailAddress(Mockito.<String>any());
        verify(jwtImplementation).generateJwtToken(Mockito.<Map<String, Object>>any(), Mockito.<String>any());
        verify(authenticationManager).authenticate(Mockito.<Authentication>any());
        ApiResponse<LoginResponse> body = actualLoginResult.getBody();
        LoginResponse data = body.getData();
        assertEquals("example@gmail.com", data.getEmailAddress());
        assertEquals("ABC123", data.getToken());
        assertEquals("lastname", data.getLastName());
        assertEquals("firstname", data.getFirstName());
        assertEquals("Success", body.getMessage());
        assertEquals(200, body.getStatusCode().intValue());
        assertEquals(200, actualLoginResult.getStatusCodeValue());
        assertEquals(HttpStatus.OK, body.getStatus());
        assertTrue(actualLoginResult.hasBody());
        assertTrue(actualLoginResult.getHeaders().isEmpty());
    }
}