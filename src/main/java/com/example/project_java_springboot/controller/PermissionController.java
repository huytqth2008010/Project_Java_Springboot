package com.example.project_java_springboot.controller;

import com.example.project_java_springboot.entity.Permission;
import com.example.project_java_springboot.service.PermissionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping(path = "api/v1/permissions")
public class PermissionController {
    private final PermissionService permissionService;

    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Permission>> findAll() {
        return ResponseEntity.ok(permissionService.findAll());
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Permission> save(@RequestBody Permission permission) {
        return ResponseEntity.status(HttpStatus.CREATED).body(permissionService.save(permission));
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/{id}")
    public ResponseEntity<Permission> update(@PathVariable Integer id, @RequestBody Permission permission) {
        Optional<Permission> optionalPermission = permissionService.findById(id);

        if (!optionalPermission.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        Permission existPermission = optionalPermission.get();
        existPermission.setName(permission.getName());
        existPermission.setUrl(permission.getUrl());
        existPermission.setMethod(permission.getMethod());

        return ResponseEntity.ok(permissionService.save(existPermission));
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{id}")
    public ResponseEntity<Permission> findById(@PathVariable Integer id) {
        Optional<Permission> optionalPermission = permissionService.findById(id);

        if (!optionalPermission.isPresent()) {
            ResponseEntity.notFound();
        }

        return ResponseEntity.ok(optionalPermission.get());
    }
}
