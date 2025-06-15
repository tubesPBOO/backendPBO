package com.example.tubespboo.repos;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.tubespboo.model.Tukang;

public interface TukangRepository extends MongoRepository<Tukang, String> {

    Tukang findByName(String name);

    boolean existsByEmail(String email);

    boolean existsByName(String name);

    boolean existsByPhoneNumber(String number);

    Tukang findByEmail(String username);
}
