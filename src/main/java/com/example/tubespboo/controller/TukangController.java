package com.example.tubespboo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.tubespboo.exception.BadRequestException;
import com.example.tubespboo.exception.DuplicateResource;
import com.example.tubespboo.model.OrderRequest;
import com.example.tubespboo.model.Project;
import com.example.tubespboo.model.Tukang;
import com.example.tubespboo.services.OrderProjectService;
import com.example.tubespboo.services.TukangService;

@RestController
@RequestMapping("/api/tukang")
public class TukangController {

    @Autowired
    private TukangService tukangService;

    @Autowired
    private OrderProjectService orderProjectService;
    @PostMapping("/register")
    public ResponseEntity<String> createTukang(@RequestBody Tukang tukang) {
        System.out.println("Received tukang registration request");
        try{
            tukangService.saveTukang(tukang);
            return ResponseEntity.ok("you've been registered as Tukang");
        }catch(BadRequestException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }catch(DuplicateResource e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @GetMapping("/getAll")
    public List<Tukang> getAllTukang() {
        return tukangService.getAllTukang();
    }
    @DeleteMapping("/deleteAccount")
    public ResponseEntity<String> deleteAccount() {
        tukangService.deleteAccount();
        return ResponseEntity.ok("Account has been deleted.");
    }
    @GetMapping("/getMyProject")
    public List<Project> getProjects(){
        return tukangService.getProjects();
    }

    @GetMapping("/getAllProjects")
    public List<Project> getAllProjects(){
        return orderProjectService.getAllProjects();
    }

    @PostMapping("/assignSelf")
    public ResponseEntity<String> AssignSelf(@RequestBody OrderRequest order){
        tukangService.AssignSelf(order.getName());
        return ResponseEntity.ok("Assigned to "+order.getName());
    }
}
