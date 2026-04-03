package com.restful.dslearn.controller;

import com.restful.dslearn.dto.OfferDTO;
import com.restful.dslearn.dto.OfferRequestDTO;
import com.restful.dslearn.service.OfferService;
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
@RequestMapping("/api/offers")
public class OfferController {

    private final OfferService offerService;

    public OfferController(OfferService offerService) {
        this.offerService = offerService;
    }

    @GetMapping
    public List<OfferDTO> findAll() {
        return offerService.findAll();
    }

    @GetMapping("/{id}")
    public OfferDTO findById(@PathVariable Long id) {
        return offerService.findById(id);
    }

    @PostMapping
    public ResponseEntity<OfferDTO> create(@Valid @RequestBody OfferRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(offerService.create(request));
    }

    @PutMapping("/{id}")
    public OfferDTO update(@PathVariable Long id, @Valid @RequestBody OfferRequestDTO request) {
        return offerService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        offerService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
