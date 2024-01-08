package com.chukwuemeka.xpresswalletapi.services.implementations;

import static org.junit.jupiter.api.Assertions.assertThrows;

import com.chukwuemeka.xpresswalletapi.setup.exceptions.XpressException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {JwtImplementation.class})
@ExtendWith(SpringExtension.class)
class JwtImplementationTest {
    @Autowired
    private JwtImplementation jwtImplementation;

    @Test
    void testExtractEmailAddressFromToken() {
        assertThrows(XpressException.class, () -> (new JwtImplementation()).extractEmailAddressFromToken("ABC123"));
    }

    @Test
    void testIsExpired() {
        assertThrows(XpressException.class, () -> (new JwtImplementation()).isExpired("ABC123"));
    }
}
