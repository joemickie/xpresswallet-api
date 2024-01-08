package com.chukwuemeka.xpresswalletapi.services;

import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * This class holds all JWT logic for authorizing all requests
 */
@Service
public interface JwtService {
    /**
     * This extracts the user's email address from the JWT token
     * @param token The JWT token sent in during any request - Request Header
     * @return Email Address - String
     */
    String extractEmailAddressFromToken(String token);

    /**
     * This generates a JWT token for a user
     * @param claims Extra data to be added to the JWT token
     * @param emailAddress Email address of the user who is generating the token
     * @return Token - JWT - String
     */
    String generateJwtToken(Map<String, Object> claims, String emailAddress);

    /**
     * This checks if the JWT token has expired or not
     * @param token JWT token sent in from the request header
     * @return Boolean - True, if it expired
     */
    Boolean isExpired(String token);
}
