package com.restful.dslearn.controller;

import com.restful.dslearn.dto.RoleDTO;
import com.restful.dslearn.dto.RoleRequestDTO;
import com.restful.dslearn.service.RoleService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    public List<RoleDTO> findAll() {
        return roleService.findAll();
    }

    @GetMapping("/{id}")
    public RoleDTO findById(@PathVariable Long id) {
        return roleService.findById(id);
    }

    @PostMapping
    public ResponseEntity<RoleDTO> create(@Valid @RequestBody RoleRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(roleService.create(request));
    }

    @PutMapping("/{id}")
    public RoleDTO update(@PathVariable Long id, @Valid @RequestBody RoleRequestDTO request) {
        return roleService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        roleService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
