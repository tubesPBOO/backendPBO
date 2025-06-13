package com.example.tubespboo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.tubespboo.exception.BadRequestException;
import com.example.tubespboo.exception.DuplicateResource;
import com.example.tubespboo.exception.ResourceNotFound;
import com.example.tubespboo.model.Admin;
import com.example.tubespboo.model.Customer;
import com.example.tubespboo.model.Material;
import com.example.tubespboo.model.Tukang;
import com.example.tubespboo.model.UpdateProfileRequest;
import com.example.tubespboo.repos.AdminRepository;
import com.example.tubespboo.repos.CustomerRepository;
import com.example.tubespboo.repos.MaterialRepository;
import com.example.tubespboo.repos.TukangRepository;

@Service
public class AdminServices extends UserServices {

    @Autowired
    private MaterialRepository materialRepository;
    @Autowired
    private MaterialService materialService;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private TukangRepository tukangRepository;

    public Admin createAdmin(Admin admin) {
        if (admin.getEmail() == null || admin.getEmail().isEmpty()) {
            throw new BadRequestException("Email is required.");
        }
        if (adminRepository.existsByEmail(admin.getEmail())) {
            throw new DuplicateResource("Email " + admin.getEmail() + " already registered.");
        }
        if (admin.getPassword() == null || admin.getPassword().isEmpty()) {
            throw new BadRequestException("Password is required.");
        }

        validatePassword(admin.getPassword());

        admin.setPassword(passwordEncoder.encode(admin.getPassword()));

        if (admin.getRole() == null || admin.getRole().isEmpty()) {
            admin.setRole("ROLE_ADMIN");
        }

        return adminRepository.save(admin);
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

    public void addMaterial(Material material) {
        if (material.getName() == null || material.getName().isEmpty()) {
            throw new BadRequestException("Material name is required.");
        }
        if (materialRepository.findByName(material.getName()) != null) {
            throw new DuplicateResource("Material with name '" + material.getName() + "' already exists.");
        }
        if (material.getStock() <= 0) {
            throw new BadRequestException("Stock cannot be empty.");
        }
        if (material.getName() == null || material.getName().isEmpty()) {
            throw new BadRequestException("Name is required.");
        }
        material.setTotrating(0);
        material.setRatingCount(0);
        materialRepository.save(material);
    }

    public ResponseEntity<String> updateMaterialStock(String name, Material m) {
        
        try{
            materialService.updateStock(name, m.getStock());
            return ResponseEntity.ok("Price Updated");
        }catch(RuntimeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    public ResponseEntity<String> updatePrice(String id, double price) {
        try{
            materialService.updatePrice(id, price);
            return ResponseEntity.ok("Price Updated");
        }catch(RuntimeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    public void updateTukangAvailability(String name, boolean available) {
        Tukang tukang = tukangRepository.findByName(name);
        if (tukang == null) {
            throw new RuntimeException("Tukang not found: " + name);
        }
        tukang.setAvailability(available);
        tukangRepository.save(tukang);
    }

    @Override
    public void viewDashboard() {

    }

    @Override
    public void updateProfile(UpdateProfileRequest updateProfile) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    public void deleteUser(String name){
        Customer cus = customerRepository.findByName(name);

        if (cus == null){
            throw new ResourceNotFound("customer with name: "+name+" not found");
        }else{
            customerRepository.delete(cus);
        }
    }

    public void deleteTukang(String name){
        Tukang tuk = tukangRepository.findByName(name);

        if (tuk == null){
            throw new ResourceNotFound("Tukang with name: "+name+" not found");
        }else{
            tukangRepository.delete(tuk);
        }
    }
}
