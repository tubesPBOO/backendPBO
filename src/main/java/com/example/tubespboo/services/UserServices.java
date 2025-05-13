package com.example.tubespboo.services;

import com.example.tubespboo.model.User;


public abstract class UserServices implements AuthServices {
    private User loggedUser;
    @Override
    public void login(String name, String pass) {
        
    }

    @Override
    public void logout() {
    }

    public abstract void viewDashboard();
    public abstract void updateProfile();
}


