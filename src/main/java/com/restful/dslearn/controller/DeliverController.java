package com.restful.dslearn.controller;

import com.restful.dslearn.dto.DeliverDTO;
import com.restful.dslearn.dto.DeliverRequestDTO;
import com.restful.dslearn.service.DeliverService;
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
@RequestMapping("/api/delivers")
public class DeliverController {

    private final DeliverService deliverService;

    public DeliverController(DeliverService deliverService) {
        this.deliverService = deliverService;
    }

    @GetMapping
    public List<DeliverDTO> findAll() {
        return deliverService.findAll();
    }

    @GetMapping("/{id}")
    public DeliverDTO findById(@PathVariable Long id) {
        return deliverService.findById(id);
    }

    @PostMapping
    public ResponseEntity<DeliverDTO> create(@Valid @RequestBody DeliverRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(deliverService.create(request));
    }

    @PutMapping("/{id}")
    public DeliverDTO update(@PathVariable Long id, @Valid @RequestBody DeliverRequestDTO request) {
        return deliverService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        deliverService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
