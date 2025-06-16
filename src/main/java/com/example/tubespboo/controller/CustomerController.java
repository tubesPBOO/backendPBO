package com.example.tubespboo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.tubespboo.exception.BadRequestException;
import com.example.tubespboo.exception.DuplicateResource;
import com.example.tubespboo.exception.ResourceNotFound;
import com.example.tubespboo.model.Customer;
import com.example.tubespboo.model.Material;
import com.example.tubespboo.model.Project;
import com.example.tubespboo.model.UpdateProfileRequest;
import com.example.tubespboo.services.CustomerServices;
import com.example.tubespboo.services.MaterialService;
import com.example.tubespboo.services.OrderProjectService;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerServices customerService;
    @Autowired
    private MaterialService materialService;
    @Autowired
    private OrderProjectService orderProjectService;

    @PostMapping("/rateTukang")
    public ResponseEntity<String> ratingTukang(@RequestBody String name, @RequestBody int rating){
        try {
            customerService.rateTukang(name, rating);
            return ResponseEntity.ok("Tukang has been rated");
        } catch (ResourceNotFound e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @GetMapping("/getCurrentUser")
    public ResponseEntity<?> getCurrentUser(){
        try {
            Customer cus = customerService.getLoggedInCustomer();
            return ResponseEntity.ok(cus);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @PostMapping("/register")
    public ResponseEntity<String> createCustomer(@RequestBody Customer customer) {
        try {
            customerService.saveCustomer(customer);
            return ResponseEntity.ok("you've been registered as Customer");
        } catch (BadRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (DuplicateResource e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @PatchMapping("/updateProfile")
    public ResponseEntity<String> updateProfile(@RequestBody UpdateProfileRequest updateProfile) {
        try {
            customerService.updateProfile(updateProfile);
            return ResponseEntity.ok("Profile Changed Sucessfully");
        } catch (DuplicateResource | ResourceNotFound err) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(err.getMessage());
        }
    }

    @GetMapping("/{name}")
    public Customer getCustomer(@PathVariable String name) {
        return customerService.getCustomerName(name);
    }

    @PostMapping("/rating/materials/{name}")
    public ResponseEntity<String> addRatings(@PathVariable String name, @RequestBody Material material) {
        try {
            materialService.addRating(name, material.getTotrating());
            return ResponseEntity.ok("Add Rating added Sucessfully");
        } catch (RuntimeException err) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err.getMessage());
        }
    }

    @PostMapping("/addProject")
    public ResponseEntity<String> addProject(@RequestBody Project project) {
        try {
            orderProjectService.addProject(project);
            return ResponseEntity.ok("Project Added");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/getMyProject")
    public List<Project> getMyProject() {
        return orderProjectService.getMyProject();
    }

    @DeleteMapping("/deleteAccount")
    public ResponseEntity<String> deleteAccount() {
        try {
            customerService.deleteAccount();
            return ResponseEntity.ok("Account deleted Sucessfully");
        } catch (RuntimeException err) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err.getMessage());

        }
    }
}
