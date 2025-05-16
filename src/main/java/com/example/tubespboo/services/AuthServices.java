package com.example.tubespboo.services;


public interface AuthServices {
    void login(String name, String pass);
    void logout();
    boolean isAdminLoggedIn();
    boolean isCustomerLoggedIn();
    boolean isTukangLoggedIn();
}