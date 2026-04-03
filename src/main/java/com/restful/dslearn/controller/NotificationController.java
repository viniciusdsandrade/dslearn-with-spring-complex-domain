package com.restful.dslearn.controller;

import com.restful.dslearn.dto.NotificationDTO;
import com.restful.dslearn.dto.NotificationRequestDTO;
import com.restful.dslearn.service.NotificationService;
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
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping
    public List<NotificationDTO> findAll() {
        return notificationService.findAll();
    }

    @GetMapping("/{id}")
    public NotificationDTO findById(@PathVariable Long id) {
        return notificationService.findById(id);
    }

    @PostMapping
    public ResponseEntity<NotificationDTO> create(@Valid @RequestBody NotificationRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(notificationService.create(request));
    }

    @PutMapping("/{id}")
    public NotificationDTO update(@PathVariable Long id, @Valid @RequestBody NotificationRequestDTO request) {
        return notificationService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        notificationService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
