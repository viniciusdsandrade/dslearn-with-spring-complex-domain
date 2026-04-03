package com.restful.dslearn.controller;

import com.restful.dslearn.dto.ReplyDTO;
import com.restful.dslearn.dto.ReplyRequestDTO;
import com.restful.dslearn.service.ReplyService;
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
@RequestMapping("/api/replies")
public class ReplyController {

    private final ReplyService replyService;

    public ReplyController(ReplyService replyService) {
        this.replyService = replyService;
    }

    @GetMapping
    public List<ReplyDTO> findAll() {
        return replyService.findAll();
    }

    @GetMapping("/{id}")
    public ReplyDTO findById(@PathVariable Long id) {
        return replyService.findById(id);
    }

    @PostMapping
    public ResponseEntity<ReplyDTO> create(@Valid @RequestBody ReplyRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(replyService.create(request));
    }

    @PutMapping("/{id}")
    public ReplyDTO update(@PathVariable Long id, @Valid @RequestBody ReplyRequestDTO request) {
        return replyService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        replyService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
