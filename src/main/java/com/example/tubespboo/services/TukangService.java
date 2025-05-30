package com.example.tubespboo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.tubespboo.exception.BadRequestException;
import com.example.tubespboo.exception.DuplicateResource;
import com.example.tubespboo.exception.ResourceNotFound;
import com.example.tubespboo.model.Order;
import com.example.tubespboo.model.Tukang;
import com.example.tubespboo.model.UpdateProfileRequest;
import com.example.tubespboo.repos.OrderRepository;
import com.example.tubespboo.repos.TukangRepository;

@Service
public class TukangService extends UserServices {

    @Autowired
    private TukangRepository tukangRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private OrderRepository orderRepository;

    public List<Order> getOrders() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!(principal instanceof Tukang)) {
            throw new RuntimeException("Current user is not a tukang");
        }

        Tukang tukang = (Tukang) principal;
        String tukangId = tukang.getId();

        List<Order> orders = orderRepository.findByTukangs_Id(tukangId);

        return orders;
    }

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
        if (tukangRepository.existsByName(tukang.getName())) {
            throw new DuplicateResource("Name " + tukang.getName() + " already registered.");
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
    public void updateProfile(UpdateProfileRequest updateProfile) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Tukang tukang = tukangRepository.findByName(authentication.getName());
        if (tukang == null || tukang.getName() == null || tukang.getName().isEmpty()) {
            throw new ResourceNotFound("Tukang with name " + authentication.getName() + " not found");
        }
        if (updateProfile.getName() != null) {
            tukang.setName(updateProfile.getName());
        }
        if (updateProfile.getEmail() != null) {
            tukang.setEmail(updateProfile.getEmail());
        }
        if (updateProfile.getPhoneNumber() != null) {
            tukang.setPhoneNumber(updateProfile.getPhoneNumber());
        }
        if (updateProfile.getPassword() != null) {
            tukang.setPassword(passwordEncoder.encode(updateProfile.getPassword()));
        }
        tukangRepository.save(tukang);
    }

    public void deleteAccount() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!(principal instanceof Tukang)) {
            throw new RuntimeException("Current user is not a tukang");
        }
        Tukang tukang = (Tukang) principal;
        tukangRepository.delete(tukang);

    }
}
