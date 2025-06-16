package com.example.tubespboo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.tubespboo.exception.ResourceNotFound;
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
    @DeleteMapping("/deleteMaterial/{name}")
    public ResponseEntity<String> deleteMaterial(@PathVariable String name){
        try{
            materialService.deleteMaterial(name);
            return ResponseEntity.ok(name+" Deleted");
        }catch(ResourceNotFound err){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err.getMessage());
        }
    }
}
