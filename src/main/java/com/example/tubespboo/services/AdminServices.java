package com.example.tubespboo.services;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.tubespboo.exception.BadRequestException;
import com.example.tubespboo.model.Material;
import com.example.tubespboo.repos.MaterialRepository;

public class AdminServices extends UserServices {
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
    public void updateMaterialStock(Material m){
        
    }
    public void updatePrice(int id,double price){
        
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
