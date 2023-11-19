package com.server.user;

import java.security.SecureRandom;
import java.util.Random;

public class OTPCreator {

    private static final String CHARACTERS = "1230456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!?-éç(";
    private static final SecureRandom RANDOM = new SecureRandom();

    public static String generate() {
        int length = pwLength();
        StringBuilder otp = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            otp.append(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length())));
        }
        return otp.toString();
    }
    public static int pwLength() {
        int min = 6;
        int max = 20;
        Random random = new Random();
        int randomNumber = random.nextInt(max - min) + min;
        return randomNumber;
    }
}