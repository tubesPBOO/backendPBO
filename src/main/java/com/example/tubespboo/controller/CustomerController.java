package com.example.tubespboo.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.tubespboo.model.Customer;
import com.example.tubespboo.model.Material;
import com.example.tubespboo.model.UpdateProfileRequest;
import com.example.tubespboo.services.CustomerServices;
import com.example.tubespboo.services.MaterialService;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerServices customerService;
    @Autowired
    private MaterialService materialService;

    @PostMapping("/register")
    public Customer createCustomer(@RequestBody Customer customer) {
        return customerService.saveCustomer(customer);
    }

    @PostMapping("/updateProfile")
    public void updateProfile(@RequestBody UpdateProfileRequest updateProfile){
        customerService.updateProfile(updateProfile);
    }
    @GetMapping("/{name}")
    public Customer getCustomer(@PathVariable String name) {
        return customerService.getCustomerName(name);
    }
    @PostMapping("/rating/{name}")
    public void addRatings(@PathVariable String name, @RequestBody Material material) {
        materialService.addRating(name, material.getTotrating());
    }

    @GetMapping
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }
}
