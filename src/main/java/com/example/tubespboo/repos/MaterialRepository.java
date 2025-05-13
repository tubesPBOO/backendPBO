package com.example.tubespboo.repos;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.tubespboo.model.Material;

public interface MaterialRepository extends MongoRepository<Material, String> {
    Material findByName(String name);
}
