package com.example.tubespboo.utils;

import java.util.Random;
import java.util.UUID;

public class Util {
    private static Random random = new Random();

    public static String generateRandomTrackingNumber() {
        return UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    public static int generateRandomId() {
        return 1000 + random.nextInt(9000);
    }
}
