package com.example.tubespboo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.tubespboo.model.Material;
import com.example.tubespboo.services.MaterialService;

@RestController
@RequestMapping("/api/materials")

public class MaterialController {
    @Autowired
    private MaterialService materialService;
    
    @GetMapping
    public List<Material> getAllMaterials() {
        return materialService.getAllMaterial();
    }
    @GetMapping("/ratingavg/{name}")
    public double getAverageRating(@PathVariable String name) {
       return materialService.getAverageRating(name);
    }
    @PostMapping("/rating/{name}")
    public void addRatings(@PathVariable String name, @RequestBody Material material) {
        materialService.addRating(name, material.getTotrating());
    }
}
