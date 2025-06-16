package com.example.tubespboo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.tubespboo.exception.BadRequestException;
import com.example.tubespboo.exception.DuplicateResource;
import com.example.tubespboo.exception.ResourceNotFound;
import com.example.tubespboo.model.Customer;
import com.example.tubespboo.model.Tukang;
import com.example.tubespboo.model.UpdateProfileRequest;
import com.example.tubespboo.repos.CustomerRepository;
import com.example.tubespboo.repos.TukangRepository;

@Service
public class CustomerServices extends UserServices {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private TukangRepository tukangRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public void rateTukang(String name, int rating) {
        Tukang tukang = tukangRepository.findByName(name);
        if (tukang == null) {
            throw new ResourceNotFound("tukang with name " + name + " doesn't exist");
        }
        tukang.addRating(rating);
        tukangRepository.save(tukang);
    }

    public Customer getLoggedInCustomer() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof Customer)) {
            throw new RuntimeException("Current User is not Customer");
        }
        Customer cus = (Customer) authentication.getPrincipal();
        return cus;
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
    public void updateProfile(UpdateProfileRequest updateProfile) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Customer customer = customerRepository.findByName(authentication.getName());
        if (customer == null || customer.getName() == null || customer.getName().isEmpty()) {
            throw new ResourceNotFound("Customer with name " + authentication.getName() + " not found");
        }
        if (updateProfile.getName() != null && !updateProfile.getName().equals(customer.getName())) {
            if (customerRepository.existsByName(updateProfile.getName())) {
                throw new DuplicateResource(updateProfile.getName() + " is already exist");
            }
            customer.setName(updateProfile.getName());
        }
        if (updateProfile.getEmail() != null && !updateProfile.getEmail().equals(customer.getEmail())) {
            String emailUsername = updateProfile.getEmail().split("@")[0];

            boolean exists = customerRepository.findAll().stream()
                    .anyMatch(c -> c.getEmail() != null && c.getEmail().split("@")[0].equals(emailUsername));

            if (exists) {
                throw new DuplicateResource(emailUsername + " is already in use");
            }

            customer.setEmail(updateProfile.getEmail());
        }

        if (updateProfile.getPhoneNumber() != null && !updateProfile.getPhoneNumber().equals(customer.getPhoneNumber())) {
            if (customerRepository.existsByPhoneNumber(updateProfile.getPhoneNumber())) {
                throw new DuplicateResource(updateProfile.getPhoneNumber() + " is already exist");
            }
            customer.setPhoneNumber(updateProfile.getPhoneNumber());
        }
        if (updateProfile.getPassword() != null) {
            customer.setPassword(passwordEncoder.encode(updateProfile.getPassword()));
        }
        if (updateProfile.getAddress() != null) {
            customer.setAddress(updateProfile.getAddress());
        }

        customerRepository.save(customer);
    }

    public void saveCustomer(Customer customer) {
        if (customerRepository.existsByEmail(customer.getEmail())) {
            throw new DuplicateResource("Email " + customer.getEmail() + " already registered.");
        }
        if (customer.getEmail() == null || customer.getEmail().isEmpty()) {
            throw new BadRequestException("Email is required.");
        }
        if (customerRepository.existsByName(customer.getName())) {
            throw new DuplicateResource("Name " + customer.getName() + " already registered.");
        }
        validatePassword(customer.getPassword());
        validatePhoneNumber(customer.getPhoneNumber());

        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        validatePassword(customer.getPassword());
        if (customer.getRole() == null || customer.getRole().isEmpty()) {
            customer.setRole("ROLE_CUSTOMER");
        }
        customerRepository.save(customer);
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

    private void validatePhoneNumber(String number) {
        if (number.length() < 11) {
            throw new BadRequestException("Phone Number must be at least 11 characters long.");
        }
        if (!number.matches("\\d+")) {
            throw new BadRequestException("Phone Number must contain only numbers.");
        }
    }

    public Customer getCustomerName(String name) {
        Customer cus = customerRepository.findByName(name);
        if (cus == null) {
            throw new ResourceNotFound("Customer with Name " + name + " not found.");
        } else {
            return cus;
        }
    }

    public void deleteCustomerByName(String name) {
        Customer cus = customerRepository.findByName(name);
        if (cus == null) {
            throw new ResourceNotFound("Customer with Name " + name + " not found.");
        } else {
            customerRepository.deleteByName(name);
        }
    }

    public void deleteAccount() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (!(principal instanceof Customer)) {
            throw new RuntimeException("Current user is not Customer");
        }
        Customer cus = (Customer) principal;
        customerRepository.delete(cus);
    }
}
