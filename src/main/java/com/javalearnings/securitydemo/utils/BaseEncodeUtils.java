package com.javalearnings.securitydemo.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
@Slf4j
public class BaseEncodeUtils {

    public String getEncodedText(String originalInput) {
        try {
            String encodedString = Base64.getEncoder().encodeToString(originalInput.getBytes());
            log.debug("encodedString {}", encodedString);
            return encodedString;
        } catch (Exception e) {
            return originalInput;
        }
    }

    public String getDecodedText(String encodedString) {
        try {
            byte[] decodedBytes = Base64.getDecoder().decode(encodedString);
            String decodedString = new String(decodedBytes);
            log.debug("decodedString {}", decodedString);
            return decodedString;
        } catch (Exception e) {
            return encodedString;
        }
    }

}
