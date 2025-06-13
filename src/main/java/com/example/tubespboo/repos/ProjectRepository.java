package com.example.tubespboo.repos;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.tubespboo.model.Project;

public interface ProjectRepository extends MongoRepository<Project, String> {
    Project findByName(String name);
    List<Project> findBylistTukang_Id(String tukangId);
    List<Project> findBycustomerId(String customerID);

}
