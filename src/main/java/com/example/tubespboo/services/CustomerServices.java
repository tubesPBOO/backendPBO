package com.example.tubespboo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

    private Customer loggedInCustomer;

    @Override
    public void login(String name, String pass) {
        Customer customer = customerRepository.findByNameAndPassword(name, pass);
        if (customer != null) {
            loggedInCustomer = customer;
            System.out.println("Login successful! Welcome, " + customer.getName());
        } else {
            System.out.println("Login failed. Invalid name or password.");
        }
    }

    @Override
    public void logout() {
        if (loggedInCustomer != null) {
            System.out.println("Logged out: " + loggedInCustomer.getName());
            loggedInCustomer = null;
        } else {
            System.out.println("No user is currently logged in.");
        }
    }

    @Override
    public void viewDashboard() {
        if (loggedInCustomer != null) {
            System.out.println("Welcome to your dashboard, " + loggedInCustomer.getName());
        } else {
            System.out.println("No user is logged in.");
        }
    }
    @Override
    public void updateProfile() {
        
    }
    public Customer getLoggedInCustomer() {
        return loggedInCustomer;
    }

    public Customer saveCustomer(Customer customer) {
        if (customerRepository.existsByEmail(customer.getEmail())) {
            throw new DuplicateResource("Email " + customer.getEmail() + " already registered.");
        }
        if (customer.getEmail() == null || customer.getEmail().isEmpty()) {
            throw new BadRequestException("Email is required.");
        }

        return customerRepository.save(customer);
    }
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
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


