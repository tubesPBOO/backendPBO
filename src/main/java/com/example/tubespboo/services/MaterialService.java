

package com.example.tubespboo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.tubespboo.exception.BadRequestException;
import com.example.tubespboo.model.Material;
import com.example.tubespboo.repos.MaterialRepository;

@Service
public class MaterialService {

    @Autowired
    private MaterialRepository materialRepository;
    public Material addMaterial(Material material) {
        if (material.getStock() <= 0) {
            throw new BadRequestException("Stock cannot be empty.");
        }
        if (material.getName() == null || material.getName().isEmpty()) {
            throw new BadRequestException("Name is required.");
        }

        return materialRepository.save(material);
    }
    public void addRating(String materialId, double rating) {
        Material material = materialRepository.findById(materialId)
                .orElseThrow(() -> new RuntimeException("Material not found"));

        material.addRating(rating);
        materialRepository.save(material);
    }

    public void updateStock(String materialId, int newStock) {
        Material material = materialRepository.findById(materialId)
                .orElseThrow(() -> new RuntimeException("Material not found"));
        material.setStock(newStock);
        materialRepository.save(material);
    }

    public void updatePrice(String materialId, double newPrice) {
        Material material = materialRepository.findById(materialId)
                .orElseThrow(() -> new RuntimeException("Material not found"));
        material.setPrice(newPrice);
        materialRepository.save(material);
    }

    public double getAverageRating(String materialId) {
        Material material = materialRepository.findById(materialId)
                .orElseThrow(() -> new RuntimeException("Material not found"));

        if (material.getRatingCount() == 0) return 0;

        return material.getTotrating() / material.getRatingCount();
    }
}

