package com.example.tubespboo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.tubespboo.model.Admin;
import com.example.tubespboo.model.Customer;
import com.example.tubespboo.repos.AdminRepository;
import com.example.tubespboo.repos.CustomerRepository;

@Service("authServiceImpl")
public class AuthServiceImpl implements AuthServices {
    
    @Autowired
    private CustomerRepository customerRepository;
    
    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private Customer loggedInCustomer;
    private Admin loggedInAdmin;

    @Override
    public void login(String name, String rawPassword) {
        Customer customer = customerRepository.findByName(name);
        if (customer != null && passwordEncoder.matches(rawPassword, customer.getPassword())) {
            loggedInCustomer = customer;
            loggedInAdmin = null;
            System.out.println("Login successful! Welcome, Customer " + customer.getName());
            return;
        }

        Admin admin = adminRepository.findByName(name);
        if (admin != null && passwordEncoder.matches(rawPassword, admin.getPassword())) {
            loggedInAdmin = admin;
            loggedInCustomer = null;
            System.out.println("Login successful! Welcome, Admin " + admin.getName());
            return;
        }

        throw new RuntimeException("Invalid name or password");
    }

    @Override
    public boolean isAdminLoggedIn() {
        return loggedInAdmin != null;
    }

    @Override
    public boolean isCustomerLoggedIn() {
        return loggedInCustomer != null;
    }

    @Override
    public void logout() {
        if (loggedInCustomer != null) {
            System.out.println("Logged out: " + loggedInCustomer.getName());
            loggedInCustomer = null;
        } else if(loggedInAdmin != null) {
            System.out.println("Logged out: " + loggedInAdmin.getName());
            loggedInAdmin = null;
        } else {
            System.out.println("No user is currently logged in.");
        }
    }

    @Override
    public boolean isTukangLoggedIn() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isTukangLoggedIn'");
    }
}