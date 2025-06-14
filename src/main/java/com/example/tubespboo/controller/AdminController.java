package com.example.tubespboo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.tubespboo.exception.BadRequestException;
import com.example.tubespboo.exception.DuplicateResource;
import com.example.tubespboo.exception.ResourceNotFound;
import com.example.tubespboo.model.Customer;
import com.example.tubespboo.model.Material;
import com.example.tubespboo.model.Tukang;
import com.example.tubespboo.services.AdminServices;
import com.example.tubespboo.services.CustomerServices;
import com.example.tubespboo.services.TukangService;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private CustomerServices customerService;
    @Autowired
    private TukangService tukangService;
    @Autowired
    private AdminServices adminServices;

    // @Autowired
    // private TukangService tukangService;
    @PostMapping("/addMaterials")
    public ResponseEntity<String> createMaterial(@RequestBody Material material) {
        try {
            adminServices.addMaterial(material);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("{\"message\": \"Material Added\"}");
        } catch (BadRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (DuplicateResource e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
        }
    }

    @PutMapping("/updateStock/{name}")
    public ResponseEntity<String> updateMaterialStock(@PathVariable String name, @RequestBody Material updatedMaterial) {
        adminServices.updateMaterialStock(name, updatedMaterial);
        return ResponseEntity.ok("Updated Sucessfully");
    }

    @PutMapping("/updatePrice/{name}")
    public ResponseEntity<String> updateMaterialPrice(@PathVariable String name, @RequestBody Material updatedMaterial) {
       return adminServices.updatePrice(name, updatedMaterial.getPrice());
    }

    @GetMapping("/getCustomersList")
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @DeleteMapping("/deleteUser/{name}")
    public ResponseEntity<String> deleteUser(@PathVariable String name) {
        try {
            adminServices.deleteUser(name);
            return ResponseEntity.ok("User Deleted Sucessfully");
        }catch(ResourceNotFound err){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err.getMessage());
        }
    }

    @DeleteMapping("/deleteTukang/{name}")
    public ResponseEntity<String> deleteTukang(@PathVariable String name) {
        try {
            adminServices.deleteTukang(name);
            return ResponseEntity.ok("Tukang Deleted Sucessfully");
        } catch (ResourceNotFound err) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err.getMessage());
        }
    }

    @GetMapping("/getTukangList")
    public List<Tukang> getAllTukang() {
        return tukangService.getAllTukang();
    }
}
