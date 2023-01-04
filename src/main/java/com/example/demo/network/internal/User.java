package com.example.demo.network.internal;

public class User {
    private static String USERNAME;

    public static String getUsername() {
        return USERNAME;
    }

    public static void setUsername(String USERNAME) {
        User.USERNAME = USERNAME;
    }
}
