

package com.example.tubespboo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.tubespboo.exception.ResourceNotFound;
import com.example.tubespboo.model.Material;
import com.example.tubespboo.repos.MaterialRepository;

@Service
public class MaterialService {

    @Autowired
    private MaterialRepository materialRepository;
    
    public void addRating(String name, double rating) {
        Material material = materialRepository.findByName(name);
        if (material == null) {
            throw new RuntimeException("Material with name '" + name + "' not found");
        }
        material.addRating(rating);
        materialRepository.save(material);
    }

    public void updateStock(String name, int newStock) {
        Material material = materialRepository.findByName(name);
        if (material == null) {
            throw new RuntimeException("Material with name '" + name + "' not found");
        }
        material.setStock(newStock);
        materialRepository.save(material);
    }

    public void updatePrice(String name, double newPrice) {
        Material material = materialRepository.findByName(name);
        if (material == null) {
            throw new RuntimeException("Material with name '" + name + "' not found");
        }
        material.setPrice(newPrice);
        materialRepository.save(material);
    }
    public List<Material> getAllMaterial() {
        return materialRepository.findAll();
    }
    public double getAverageRating(String name) {
       Material material = materialRepository.findByName(name);
        if (material == null) {
            throw new RuntimeException("Material with name '" + name + "' not found");
        }
        if (material.getRatingCount() == 0) return 0;

        return material.getTotrating() / material.getRatingCount();
    }
    public void deleteMaterial(String name){
       Material material = materialRepository.findByName(name);
        if (material == null) {
            throw new ResourceNotFound("Material with name '" + name + "' not found");
        }
        materialRepository.delete(material);
    }
}

