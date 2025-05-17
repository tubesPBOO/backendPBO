package com.example.tubespboo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.tubespboo.model.LoginRequest;
import com.example.tubespboo.services.AuthServiceImpl;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    @Qualifier("authServiceImpl")
    private AuthServiceImpl authService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        try {
            authService.login(loginRequest.getName(), loginRequest.getPass());

            if (authService.isAdminLoggedIn()) {
                return ResponseEntity.ok("Logged in as Admin, Welcome "+loginRequest.getName());
            } else if (authService.isCustomerLoggedIn()) {
                return ResponseEntity.ok("Logged in as Customer, Welcome "+loginRequest.getName());
            } else if (authService.isTukangLoggedIn()) {
                return ResponseEntity.ok("Logged in as Tukang, Welcome "+loginRequest.getName());
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed");
            }
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }


    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        authService.logout();
        SecurityContextHolder.clearContext(); 
        return ResponseEntity.ok("Logged out");
    }
}