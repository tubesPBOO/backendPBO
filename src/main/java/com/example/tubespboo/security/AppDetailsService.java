package com.example.tubespboo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.tubespboo.model.Admin;
import com.example.tubespboo.model.Customer;
import com.example.tubespboo.model.Tukang;
import com.example.tubespboo.repos.AdminRepository;
import com.example.tubespboo.repos.CustomerRepository;
import com.example.tubespboo.repos.TukangRepository;

@Service
public class AppDetailsService implements UserDetailsService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private TukangRepository tukangRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Admin admin = adminRepository.findByEmail(email);
        if (admin != null) return admin;

        Customer customer = customerRepository.findByEmail(email);
        if (customer != null) return customer;

        Tukang tukang = tukangRepository.findByEmail(email);
        if (tukang != null) return tukang;

        throw new UsernameNotFoundException("User not found with email: " + email);
    }
}

