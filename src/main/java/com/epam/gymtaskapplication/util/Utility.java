package com.epam.gymtaskapplication.util;

import org.slf4j.MDC;

import java.util.UUID;
import java.security.SecureRandom;
import java.util.Base64;

public class Utility {
    public String generateTransactionId(){
        return UUID.randomUUID().toString();
    }
    public String generatePassword() {
        int length = 10;
        SecureRandom secureRandom = new SecureRandom();
        byte[] randomBytes = new byte[length];
        secureRandom.nextBytes(randomBytes);
        String randomString = Base64.getEncoder().encodeToString(randomBytes);
        randomString = randomString.replaceAll("[^a-zA-Z0-9]", "");
        randomString = randomString.substring(0, Math.min(length, randomString.length()));
        return randomString;
    }
    public void instantiateTransaction(){
        MDC.put("transactionId",generateTransactionId());
    }
    public void closeTransaction(){
        MDC.remove("transactionId");
    }
}
