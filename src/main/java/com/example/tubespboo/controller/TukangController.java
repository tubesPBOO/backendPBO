package com.example.tubespboo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.tubespboo.model.Tukang;
import com.example.tubespboo.services.TukangService;

@RestController
@RequestMapping("/api/tukang")
public class TukangController {

    @Autowired
    private TukangService tukangService;

    @PostMapping("/register")
    public Tukang createTukang(@RequestBody Tukang tukang) {
        System.out.println("Received tukang registration request");
        return tukangService.saveTukang(tukang);
    }

    @GetMapping("/getAll")
    public List<Tukang> getAllTukang() {
        return tukangService.getAllTukang();
    }
}
