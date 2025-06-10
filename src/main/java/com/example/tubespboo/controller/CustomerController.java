package com.example.tubespboo.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @PostMapping("/register")
    public Customer createCustomer(@RequestBody Customer customer) {
        return customerService.saveCustomer(customer);
    }

    @PutMapping("/updateProfile")
    public ResponseEntity<String> updateProfile(@RequestBody UpdateProfileRequest updateProfile){
        customerService.updateProfile(updateProfile);
        return ResponseEntity.ok("Profile Changed Sucessfully");
    }
    @GetMapping("/{name}")
    public Customer getCustomer(@PathVariable String name) {
        return customerService.getCustomerName(name);
    }
    @PostMapping("/rating/materials/{name}")
    public ResponseEntity<String> addRatings(@PathVariable String name, @RequestBody Material material) {
        materialService.addRating(name, material.getTotrating());
        return ResponseEntity.ok("Add Rating added Sucessfully");
    }

    @PostMapping("/addProject")
    public Project addProject(@RequestBody Project project){
        return orderProjectService.addProject(project);
    }
    @GetMapping("/getMyProject")
    public List<Project> getMyProject(){
        return orderProjectService.getMyProject();
    }
    @DeleteMapping("/deleteAccount")
    public ResponseEntity<String> deleteAccount(){
        customerService.deleteAccount();
        return ResponseEntity.ok("Account deleted Sucessfully");
    }
}
