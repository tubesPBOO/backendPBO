package com.example.tubespboo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.tubespboo.exception.BadRequestException;
import com.example.tubespboo.exception.DuplicateResource;
import com.example.tubespboo.model.Material;
import com.example.tubespboo.repos.MaterialRepository;

@Service
public class AdminServices extends UserServices {
    @Autowired
    private MaterialRepository materialRepository;
    @Autowired
    private MaterialService materialService;
    public Material addMaterial(Material material) {
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

        return materialRepository.save(material);
    }
    public void updateMaterialStock(String name,Material m){
        materialService.updateStock(name, m.getStock());
    }
    public void updatePrice(String id,double price){
        materialService.updatePrice(id, price);
    }
    //public void registerWorker(Tukang){

    //}
    public void assignTukangToOrder(int orderId,int tukangId){

    }
    public void UpdateTukangAvailability(int tukangId,boolean available){

    }
    @Override
    public void viewDashboard(){
        
    }

    @Override
    public void updateProfile() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
