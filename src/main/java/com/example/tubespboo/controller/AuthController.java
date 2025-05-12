package com.example.tubespboo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.tubespboo.services.CustomerServices;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private CustomerServices customerService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String name, @RequestParam String pass) {
        customerService.login(name, pass);
        return ResponseEntity.ok("Logged in");
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        customerService.logout();
        return ResponseEntity.ok("Logged out");
    }
}

