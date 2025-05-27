package com.example.tubespboo.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.tubespboo.model.Admin;
import com.example.tubespboo.model.Customer;
import com.example.tubespboo.model.Tukang;
import com.example.tubespboo.services.AuthServiceImpl;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CustomAuthenticationFilter extends OncePerRequestFilter {

    private final AuthServiceImpl authService;

    public CustomAuthenticationFilter(AuthServiceImpl authService) {
        this.authService = authService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String uri = request.getRequestURI();
        System.out.println("Request URI: " + uri);
        if (uri.startsWith("/api/customers/register") || 
            uri.startsWith("/api/tukang/register") || 
            uri.startsWith("/auth/")) {
            filterChain.doFilter(request, response);
            return;
        }

        if (authService.isAdminLoggedIn()) {
            Admin admin = authService.getLoggedInAdmin();
            Authentication auth = new UsernamePasswordAuthenticationToken(
                admin, null,
                AuthorityUtils.createAuthorityList("ROLE_ADMIN")
            );
            SecurityContextHolder.getContext().setAuthentication(auth);
        } 
        else if (authService.isCustomerLoggedIn()) {
            Customer customer = authService.getLoggedInCustomer();
            Authentication auth = new UsernamePasswordAuthenticationToken(
                customer, null,
                AuthorityUtils.createAuthorityList("ROLE_CUSTOMER")
            );
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        else if (authService.isTukangLoggedIn()) {
            Tukang tukang = authService.getLoggedInTukang();
            Authentication auth = new UsernamePasswordAuthenticationToken(
                tukang, null,
                AuthorityUtils.createAuthorityList("ROLE_TUKANG")
            );
            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        filterChain.doFilter(request, response);
    }
}
