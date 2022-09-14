package com.example.project_java_springboot.repository;

import com.example.project_java_springboot.entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RolesRepository extends JpaRepository<Roles, String> {
    List<Roles> findAllByNameIn(String[] roles);
    Optional<Roles> findByName(String name);
}
