package com.example.moveoapp;

import java.util.UUID;
//A class for creating a unique key for a note
public class RandomKeyGenerator {
    public static String generateRandomKey() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
}
