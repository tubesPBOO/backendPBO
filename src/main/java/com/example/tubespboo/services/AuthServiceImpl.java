package com.example.tubespboo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.tubespboo.model.Admin;
import com.example.tubespboo.model.Customer;
import com.example.tubespboo.model.Tukang;
import com.example.tubespboo.repos.AdminRepository;
import com.example.tubespboo.repos.CustomerRepository;
import com.example.tubespboo.repos.TukangRepository;

@Service("authServiceImpl")
public class AuthServiceImpl implements AuthServices {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private TukangRepository tukangRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private Customer loggedInCustomer;
    private Admin loggedInAdmin;
    private Tukang loggedInTukang;

    @Override
    public void login(String name, String rawPassword) {
        Customer customer = customerRepository.findByName(name);
        if (customer != null && passwordEncoder.matches(rawPassword, customer.getPassword())) {
            loggedInCustomer = customer;
            loggedInAdmin = null;
            loggedInTukang = null;
            System.out.println("Login successful! Welcome, Customer " + customer.getName());
            return;
        }

        Admin admin = adminRepository.findByName(name);
        if (admin != null && passwordEncoder.matches(rawPassword, admin.getPassword())) {
            loggedInAdmin = admin;
            loggedInCustomer = null;
            loggedInTukang = null;
            System.out.println("Login successful! Welcome, Admin " + admin.getName());
            return;
        }
        Tukang tukang = tukangRepository.findByName(name);
        if (tukang != null && passwordEncoder.matches(rawPassword, tukang.getPassword())) {
            loggedInTukang = tukang;
            loggedInCustomer = null;
            loggedInAdmin = null;
            System.out.println("Login successful! Welcome, Tukang " + tukang.getName());
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
        } else if (loggedInAdmin != null) {
            System.out.println("Logged out: " + loggedInAdmin.getName());
            loggedInAdmin = null;
        } else if (loggedInAdmin != null) {
            System.out.println("Logged out: " + loggedInTukang.getName());
            loggedInTukang = null;
        } else {
            System.out.println("No user is currently logged in.");
        }
    }

    @Override
    public boolean isTukangLoggedIn() {
        return loggedInTukang != null;
    }

    public Customer getLoggedInCustomer() {
        return loggedInCustomer;

    }
    public Tukang getLoggedInTukang() {
        return loggedInTukang;

    }
    public Admin getLoggedInAdmin() {
        return loggedInAdmin;

    }
}
