package com.restful.dslearn.controller;

import com.restful.dslearn.dto.ResourceDTO;
import com.restful.dslearn.dto.ResourceRequestDTO;
import com.restful.dslearn.service.ResourceService;
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
@RequestMapping("/api/resources")
public class ResourceController {

    private final ResourceService resourceService;

    public ResourceController(ResourceService resourceService) {
        this.resourceService = resourceService;
    }

    @GetMapping
    public List<ResourceDTO> findAll() {
        return resourceService.findAll();
    }

    @GetMapping("/{id}")
    public ResourceDTO findById(@PathVariable Long id) {
        return resourceService.findById(id);
    }

    @PostMapping
    public ResponseEntity<ResourceDTO> create(@Valid @RequestBody ResourceRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(resourceService.create(request));
    }

    @PutMapping("/{id}")
    public ResourceDTO update(@PathVariable Long id, @Valid @RequestBody ResourceRequestDTO request) {
        return resourceService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        resourceService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
