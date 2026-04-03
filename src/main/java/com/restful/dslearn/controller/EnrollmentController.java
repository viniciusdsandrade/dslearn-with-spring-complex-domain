package com.restful.dslearn.controller;

import com.restful.dslearn.dto.EnrollmentDTO;
import com.restful.dslearn.dto.EnrollmentRequestDTO;
import com.restful.dslearn.service.EnrollmentService;
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
@RequestMapping("/api/enrollments")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @GetMapping
    public List<EnrollmentDTO> findAll() {
        return enrollmentService.findAll();
    }

    @GetMapping("/user/{userId}/offer/{offerId}")
    public EnrollmentDTO findById(@PathVariable Long userId, @PathVariable Long offerId) {
        return enrollmentService.findById(userId, offerId);
    }

    @PostMapping
    public ResponseEntity<EnrollmentDTO> create(@Valid @RequestBody EnrollmentRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(enrollmentService.create(request));
    }

    @PutMapping("/user/{userId}/offer/{offerId}")
    public EnrollmentDTO update(@PathVariable Long userId, @PathVariable Long offerId,
                               @Valid @RequestBody EnrollmentRequestDTO request) {
        return enrollmentService.update(userId, offerId, request);
    }

    @DeleteMapping("/user/{userId}/offer/{offerId}")
    public ResponseEntity<Void> delete(@PathVariable Long userId, @PathVariable Long offerId) {
        enrollmentService.delete(userId, offerId);
        return ResponseEntity.noContent().build();
    }
}
