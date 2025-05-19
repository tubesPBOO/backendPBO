package com.example.tubespboo.repos;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.tubespboo.model.Admin;

public interface AdminRepository extends MongoRepository<Admin, String> {
    Admin findByNameAndPassword(String name, String password);
    boolean existsByEmail(String email); 
    Admin findByName(String name);
    Admin findByEmail(String username);
}
