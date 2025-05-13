package com.example.tubespboo.services;

import org.springframework.stereotype.Service;

@Service
public interface AuthServices {
    void login(String name,String pass);
    void logout();
}
