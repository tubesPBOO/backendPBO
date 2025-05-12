package com.example.tubespboo.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.tubespboo.model.Customer;
import com.example.tubespboo.services.CustomerServices;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerServices customerService;

    @PostMapping
    public Customer createCustomer(@RequestBody Customer customer) {
        return customerService.saveCustomer(customer);
    }

    @GetMapping("/{id}")
    public Customer getCustomer(@PathVariable String id) {
        return customerService.getCustomerById(id);
    }
}
