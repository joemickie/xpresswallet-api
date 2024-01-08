package com.chukwuemeka.xpresswalletapi.setup.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * This is the XpressWallet Configuration that contains most beans used for the application
 * NOTE: Not security configurations
 */
@Configuration
public class XpressWalletConfiguration {
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
