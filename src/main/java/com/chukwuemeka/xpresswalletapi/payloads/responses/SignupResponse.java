package com.chukwuemeka.xpresswalletapi.payloads.responses;

/**
 * This is the response for authentication - SIGNUP
 * @param email - The email address of the user
 * @param firstName - The first name of the user
 * @param lastName - The last name of the user
 */
public record SignupResponse(
        String email,
        String firstName,
        String lastName
) {
}
