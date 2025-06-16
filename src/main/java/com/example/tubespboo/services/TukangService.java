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
import com.example.tubespboo.model.Project;
import com.example.tubespboo.model.Tukang;
import com.example.tubespboo.model.UpdateProfileRequest;
import com.example.tubespboo.repos.ProjectRepository;
import com.example.tubespboo.repos.TukangRepository;

@Service
public class TukangService extends UserServices {

    @Autowired
    private TukangRepository tukangRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ProjectRepository projectRepository;

    public List<Project> getProjects() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!(principal instanceof Tukang)) {
            throw new RuntimeException("Current user is not a tukang");
        }

        Tukang tukang = (Tukang) principal;
        String tukangId = tukang.getId();

        List<Project> project = projectRepository.findBylistTukang_Id(tukangId);

        return project;
    }

    public void saveTukang(Tukang tukang) {
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
        validatePhoneNumber(tukang.getPhoneNumber());
        
        tukang.setPassword(passwordEncoder.encode(tukang.getPassword()));

        if (tukang.getRole() == null || tukang.getRole().isEmpty()) {
            tukang.setRole("ROLE_TUKANG");
        }

        tukangRepository.save(tukang);
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

    private void validatePhoneNumber(String number) {
        if (number.length() < 11) {
            throw new BadRequestException("Phone Number must be at least 11 characters long.");
        }
        if (!number.matches("\\d+")) {
            throw new BadRequestException("Phone Number must contain only numbers.");
        }
    }

    @Override
    public void viewDashboard() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'viewDashboard'");
    }

    @Override
    public void updateProfile(UpdateProfileRequest updateProfile) {
        Tukang tukang = (Tukang) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (tukang == null || tukang.getName() == null || tukang.getName().isEmpty()) {
            throw new ResourceNotFound("Tukang with name " + tukang.getName() + " not found");
        }
        if (updateProfile.getName() != null && !updateProfile.getName().equals(tukang.getName())) {
            if (tukangRepository.existsByName(updateProfile.getName())) {
                throw new DuplicateResource(updateProfile.getName() + " already exist");
            }
            tukang.setName(updateProfile.getName());
        }
        if (updateProfile.getEmail() != null && !updateProfile.getEmail().equals(tukang.getEmail())) {
            String emailUsername = updateProfile.getEmail().split("@")[0];

            boolean exists = tukangRepository.findAll().stream()
                    .anyMatch(c -> c.getEmail() != null && c.getEmail().split("@")[0].equals(emailUsername));

            if (exists) {
                throw new DuplicateResource(emailUsername + " is already in use");
            }

            tukang.setEmail(updateProfile.getEmail());
        }

        if (updateProfile.getPhoneNumber() != null && !updateProfile.getPhoneNumber().equals(tukang.getPhoneNumber())) {
            if (tukangRepository.existsByPhoneNumber(updateProfile.getPhoneNumber())) {
                throw new DuplicateResource(updateProfile.getPhoneNumber() + " already exist");
            }
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

    public void AssignSelf(String name) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!(principal instanceof Tukang)) {
            throw new RuntimeException("Current user is not a tukang");
        }

        Tukang tukang = (Tukang) principal;
        Project project = projectRepository.findByName(name);
        if (project == null) {
            throw new ResourceNotFound("Project with name " + name + " not found");
        }

        List<Tukang> currentTukangs = project.getListTukang();
        if (currentTukangs.stream().anyMatch(t -> t.getId().equals(tukang.getId()))) {
            throw new RuntimeException("You are already assigned to this project");
        }

        int tukangCount = project.getJumTukang();
        if (currentTukangs.size() >= tukangCount) {
            throw new RuntimeException("This project does not need another tukang");
        }

        project.addTukang(tukang);
        tukang.setAvailability(false);
        tukangRepository.save(tukang);

        if (project.getListTukang().size() == tukangCount) {
            project.setStatus("On Progress");
        }

        projectRepository.save(project);
    }

    public Tukang getLoggedTukang() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof Tukang)) {
            throw new RuntimeException("Current User is not Tukang");
        }
        Tukang tukang = (Tukang) authentication.getPrincipal();
        return tukang;
    }

}
