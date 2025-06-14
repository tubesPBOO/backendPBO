package com.example.tubespboo.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.tubespboo.model.Customer;
import com.example.tubespboo.model.OrderProjectDetails;
import com.example.tubespboo.model.Project;
import com.example.tubespboo.repos.CustomerRepository;
import com.example.tubespboo.repos.OrderProjectDetailsRepository;
import com.example.tubespboo.repos.ProjectRepository;
import com.example.tubespboo.utils.Util;

@Service
public class OrderProjectService {

    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private OrderProjectDetailsRepository orderProjectDetailsRepository;
    @Autowired
    private CustomerRepository customerRepository;

    public void addProject(Project project) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (!(principal instanceof Customer)) {
            throw new RuntimeException("Current user is not a customer");
        }

        Customer cus = (Customer) principal;

        project.setId(Util.generateRandomId());
        project.setPrice(project.getTotalPrice());
        project.setCustomer(cus.getId());
        project.setStatus("Looking for Tukang");
        projectRepository.save(project);

        OrderProjectDetails details = new OrderProjectDetails();
        details.setProject(project);
        details.setId(project.getId());
        details.setPayId(Util.generateRandomId());
        details.setPayDate(new Date());
        details.setStatus("not yet paid");

        orderProjectDetailsRepository.save(details);

        cus.addProject(project);
        customerRepository.save(cus);
    }

    public List<Project> getAllProjects(){
        return projectRepository.findAll();
    }
    public List<Project> getMyProject(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (!(principal instanceof Customer)) {
            throw new RuntimeException("Current user is not a customer");
        }
        Customer cus = (Customer)principal;

        List<Project> project = projectRepository.findBycustomerId(cus.getId());

        return project;
    }
}
