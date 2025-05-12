package com.example.tubespboo.services;


public abstract class UserServices implements AuthServices {
    @Override
    public void login(String name, String pass) {
    }

    @Override
    public void logout() {
    }

    public abstract void viewDashboard();
}


