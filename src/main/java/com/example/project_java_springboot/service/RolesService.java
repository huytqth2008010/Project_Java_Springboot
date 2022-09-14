package com.example.project_java_springboot.service;

import com.example.project_java_springboot.entity.Roles;
import com.example.project_java_springboot.repository.RolesRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RolesService {
    private final RolesRepository rolesRepository;

    public RolesService(RolesRepository rolesRepository) {
        this.rolesRepository = rolesRepository;
    }

    public List<Roles> findAll() {
        return rolesRepository.findAll();
    }

    public Optional<Roles> findById(String id) {
        return rolesRepository.findById(id);
    }

    public Roles save(Roles roles) {
        return rolesRepository.save(roles);
    }

    public List<Roles> findAllByNameIn(String[] roles) {
        return rolesRepository.findAllByNameIn(roles);
    }

    public void deleteById(String id) {
        rolesRepository.deleteById(id);
    }
}
