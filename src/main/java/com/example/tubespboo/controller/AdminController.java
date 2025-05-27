package com.example.tubespboo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.tubespboo.model.Material;
import com.example.tubespboo.services.AdminServices;
import com.example.tubespboo.services.CustomerServices;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    @Autowired
    private CustomerServices customerService;

    @Autowired
    private AdminServices adminServices;
    // @Autowired
    // private TukangService tukangService;
    @DeleteMapping("/delete-customer/{name}")
    public ResponseEntity<String> deleteCustomer(@PathVariable String name){
        customerService.deleteCustomerByName(name);
        return ResponseEntity.ok("Customer with name "+name+" deleted Sucessfully");
    }

    @PostMapping("/materials")
    public Material createMaterial(@RequestBody Material material) {
        return adminServices.addMaterial(material);
    }

    @PutMapping("/stock/{name}")
    public ResponseEntity<String> updateMaterialStock(@PathVariable String name, @RequestBody Material updatedMaterial) {
        adminServices.updateMaterialStock(name,updatedMaterial);
        return ResponseEntity.ok("Updated Sucessfully");
    }
    @PutMapping("/price/{name}")
    public ResponseEntity<String> updateMaterialPrice(@PathVariable String name, @RequestBody Material updatedMaterial) {
        adminServices.updatePrice(name, updatedMaterial.getPrice());
        return ResponseEntity.ok("Updated Sucessfully");
    }
}
