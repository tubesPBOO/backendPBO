package com.example.tubespboo.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.tubespboo.services.AuthServiceImpl;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CustomAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private AuthServiceImpl authService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        if (request.getRequestURI().startsWith("/auth/login")) {
            filterChain.doFilter(request, response);
            return;
        }
        
        if (authService.isAdminLoggedIn()) {
            Authentication auth = new UsernamePasswordAuthenticationToken(
                "admin", null, 
                AuthorityUtils.createAuthorityList("ROLE_ADMIN")
            );
            SecurityContextHolder.getContext().setAuthentication(auth);
        } 
        else if (authService.isCustomerLoggedIn()) {
            Authentication auth = new UsernamePasswordAuthenticationToken(
                "customer", null, 
                AuthorityUtils.createAuthorityList("ROLE_CUSTOMER")
            );
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        
        filterChain.doFilter(request, response);
    }
}
