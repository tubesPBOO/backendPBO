package com.example.tubespboo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.tubespboo.exception.BadRequestException;
import com.example.tubespboo.exception.DuplicateResource;
import com.example.tubespboo.exception.ResourceNotFound;
import com.example.tubespboo.model.Customer;
import com.example.tubespboo.repos.CustomerRepository;

@Service
public class CustomerServices extends UserServices {

    @Autowired
    private CustomerRepository customerRepository;
   
    public Customer getLoggedInCustomer() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof Customer) {
            return (Customer) authentication.getPrincipal();
        }
        return null;
    }
    @Override
    public void viewDashboard() {
        Customer loggedInCustomer = getLoggedInCustomer();
        if (loggedInCustomer != null) {
            System.out.println("Welcome to your dashboard, " + loggedInCustomer.getName());
        } else {
            System.out.println("No user is logged in.");
        }
    }

    @Override
    public void updateProfile() {
        
    }

    public Customer saveCustomer(Customer customer) {
        if (customerRepository.existsByEmail(customer.getEmail())) {
            throw new DuplicateResource("Email " + customer.getEmail() + " already registered.");
        }
        if (customer.getEmail() == null || customer.getEmail().isEmpty()) {
            throw new BadRequestException("Email is required.");
        }
        validatePassword(customer.getPassword());
        if (customer.getRole() == null || customer.getRole().isEmpty()) {
            customer.setRole("ROLE_CUSTOMER");
        }
        return customerRepository.save(customer);
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    private void validatePassword(String password) {
        if (password.length() < 8) {
            throw new BadRequestException("Password must be at least 8 characters long.");
        }
        if (!password.matches(".*[A-Z].*")) {
            throw new BadRequestException("Password must contain at least one uppercase letter.");
        }
        if (!password.matches(".*\\d.*")) {
            throw new BadRequestException("Password must contain at least one digit.");
        }
    }
    public Customer getCustomerName(String name) {
        Customer cus = customerRepository.findByName(name);
        if (cus == null ){
            throw new ResourceNotFound("Customer with Name " + name + " not found.");
        }else{
            return cus;
        }
    }
    public void deleteCustomerByName(String name){
        Customer cus = customerRepository.findByName(name);
        if (cus == null ){
            throw new ResourceNotFound("Customer with Name " + name + " not found.");
        }else{
            customerRepository.deleteByName(name);
        }
    }

}


