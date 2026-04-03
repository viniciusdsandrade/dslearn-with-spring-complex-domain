package com.restful.dslearn.controller;

import com.restful.dslearn.dto.SectionDTO;
import com.restful.dslearn.dto.SectionRequestDTO;
import com.restful.dslearn.service.SectionService;
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
@RequestMapping("/api/sections")
public class SectionController {

    private final SectionService sectionService;

    public SectionController(SectionService sectionService) {
        this.sectionService = sectionService;
    }

    @GetMapping
    public List<SectionDTO> findAll() {
        return sectionService.findAll();
    }

    @GetMapping("/{id}")
    public SectionDTO findById(@PathVariable Long id) {
        return sectionService.findById(id);
    }

    @PostMapping
    public ResponseEntity<SectionDTO> create(@Valid @RequestBody SectionRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(sectionService.create(request));
    }

    @PutMapping("/{id}")
    public SectionDTO update(@PathVariable Long id, @Valid @RequestBody SectionRequestDTO request) {
        return sectionService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        sectionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
