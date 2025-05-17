package com.example.tubespboo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.tubespboo.exception.BadRequestException;
import com.example.tubespboo.exception.DuplicateResource;
import com.example.tubespboo.model.Tukang;
import com.example.tubespboo.repos.TukangRepository;

@Service
public class TukangService extends UserServices {
    @Autowired
    private TukangRepository tukangRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public Tukang saveTukang(Tukang tukang) {
        if (tukang.getEmail() == null || tukang.getEmail().isEmpty()) {
            throw new BadRequestException("Email is required.");
        }
        if (tukangRepository.existsByEmail(tukang.getEmail())) {
            throw new DuplicateResource("Email " + tukang.getEmail() + " already registered.");
        }
        if (tukang.getPassword() == null || tukang.getPassword().isEmpty()) {
            throw new BadRequestException("Password is required.");
        }

        validatePassword(tukang.getPassword());

        tukang.setPassword(passwordEncoder.encode(tukang.getPassword()));

        if (tukang.getRole() == null || tukang.getRole().isEmpty()) {
            tukang.setRole("ROLE_TUKANG");
        }

        return tukangRepository.save(tukang);
    }
    public List<Tukang> getAllTukang() {
        return tukangRepository.findAll();
    }
    private void validatePassword(String password) {
        if (password.length() < 8) {
            throw new BadRequestException("Password must be at least 8 characters long.");
        }
        if (!password.matches(".*[A-Z].*")) {
            throw new BadRequestException("Password must contain at least one uppercase letter.");
        }
        if (!password.matches(".*\\d.*")) {
            throw new BadRequestException("Password must contain at least one digit.");
        }
    }

    @Override
    public void viewDashboard() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'viewDashboard'");
    }

    @Override
    public void updateProfile() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateProfile'");
    }
 
}
