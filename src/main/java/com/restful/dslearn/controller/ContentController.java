package com.restful.dslearn.controller;

import com.restful.dslearn.dto.ContentDTO;
import com.restful.dslearn.dto.ContentRequestDTO;
import com.restful.dslearn.service.ContentService;
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
@RequestMapping("/api/contents")
public class ContentController {

    private final ContentService contentService;

    public ContentController(ContentService contentService) {
        this.contentService = contentService;
    }

    @GetMapping
    public List<ContentDTO> findAll() {
        return contentService.findAll();
    }

    @GetMapping("/{id}")
    public ContentDTO findById(@PathVariable Long id) {
        return contentService.findById(id);
    }

    @PostMapping
    public ResponseEntity<ContentDTO> create(@Valid @RequestBody ContentRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(contentService.create(request));
    }

    @PutMapping("/{id}")
    public ContentDTO update(@PathVariable Long id, @Valid @RequestBody ContentRequestDTO request) {
        return contentService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        contentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
