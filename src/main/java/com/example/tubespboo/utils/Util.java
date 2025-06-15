package com.example.tubespboo.utils;

import java.util.Random;
import java.util.UUID;

public class Util {
private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final Random random = new Random();

    public static String generateRandomString(int length) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(CHARACTERS.length());
            result.append(CHARACTERS.charAt(index));
        }

        return result.toString();
    }
    public static String generateRandomTrackingNumber() {
        return UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    public static int generateRandomId() {
        return 1000 + random.nextInt(9000);
    }
}
