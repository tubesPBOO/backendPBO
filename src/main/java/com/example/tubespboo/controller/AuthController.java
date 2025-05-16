package com.example.tubespboo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
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
                // Create authentication token with ADMIN role
                Authentication auth = new UsernamePasswordAuthenticationToken(
                    loginRequest.getName(), null, 
                    AuthorityUtils.createAuthorityList("ROLE_ADMIN")
                );
                SecurityContextHolder.getContext().setAuthentication(auth);
                return ResponseEntity.ok("Logged in as Admin");
            } else if (authService.isCustomerLoggedIn()) {
                // Create authentication token with CUSTOMER role
                Authentication auth = new UsernamePasswordAuthenticationToken(
                    loginRequest.getName(), null, 
                    AuthorityUtils.createAuthorityList("ROLE_CUSTOMER")
                );
                SecurityContextHolder.getContext().setAuthentication(auth);
                return ResponseEntity.ok("Logged in as Customer");
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
        SecurityContextHolder.clearContext(); // Clear Spring Security context
        return ResponseEntity.ok("Logged out");
    }
}