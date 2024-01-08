package com.chukwuemeka.xpresswalletapi.utils;

import com.chukwuemeka.xpresswalletapi.setup.exceptions.XpressException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.apache.commons.codec.binary.Hex;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class PayUtils {
    @SneakyThrows
    public static String calculateHMAC512(Object data, String key) {
        ObjectMapper mapper = new ObjectMapper();
        var dataString = mapper.writeValueAsString(data);

        String HMAC_SHA512 = "HmacSHA512";

        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), HMAC_SHA512);
        Mac mac;
        try {
            mac = Mac.getInstance(HMAC_SHA512);
            mac.init(secretKeySpec);

            return Hex.encodeHexString(mac.doFinal(dataString.getBytes()));
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new XpressException(e.getMessage());
        }
    }

    public static String generateRequestId() {
        StringBuilder result = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++){
            result.append(random.nextInt(10));
        }
        return result.toString();
    }
}
