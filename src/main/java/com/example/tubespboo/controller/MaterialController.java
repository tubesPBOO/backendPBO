package com.example.tubespboo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.tubespboo.model.Material;
import com.example.tubespboo.services.AdminServices;
import com.example.tubespboo.services.MaterialService;

@RestController
@RequestMapping("/api/materials")

public class MaterialController {
    @Autowired
    private MaterialService materialService;
    private AdminServices adminServices;
    @PostMapping
    public Material createCustomer(@RequestBody Material material) {
        return adminServices.addMaterial(material);
    }
}
