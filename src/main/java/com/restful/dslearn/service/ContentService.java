package com.restful.dslearn.service;

import com.restful.dslearn.dto.ContentDTO;
import com.restful.dslearn.dto.ContentRequestDTO;
import com.restful.dslearn.entity.Content;
import com.restful.dslearn.entity.Section;
import com.restful.dslearn.repository.ContentRepository;
import com.restful.dslearn.repository.SectionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ContentService {

    private final ContentRepository contentRepository;
    private final SectionRepository sectionRepository;

    public ContentService(ContentRepository contentRepository, SectionRepository sectionRepository) {
        this.contentRepository = contentRepository;
        this.sectionRepository = sectionRepository;
    }

    public List<ContentDTO> findAll() {
        return contentRepository.findAll().stream()
                .map(this::toDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public ContentDTO findById(Long id) {
        Content content = contentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Content not found"));
        return toDTO(content);
    }

    @Transactional
    public ContentDTO create(ContentRequestDTO request) {
        Section section = sectionRepository.findById(request.sectionId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Section not found"));
        Content content = new Content();
        content.setTitle(request.title());
        content.setPosition(request.position());
        content.setSection(section);
        content.setText(request.text());
        content.setVideoUri(request.videoUri());
        return toDTO(contentRepository.save(content));
    }

    @Transactional
    public ContentDTO update(Long id, ContentRequestDTO request) {
        Content content = contentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Content not found"));
        Section section = sectionRepository.findById(request.sectionId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Section not found"));
        content.setTitle(request.title());
        content.setPosition(request.position());
        content.setSection(section);
        content.setText(request.text());
        content.setVideoUri(request.videoUri());
        return toDTO(content);
    }

    @Transactional
    public void delete(Long id) {
        if (!contentRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Content not found");
        }
        contentRepository.deleteById(id);
    }

    private ContentDTO toDTO(Content content) {
        return new ContentDTO(
                content.getId(),
                content.getTitle(),
                content.getPosition(),
                content.getSection().getId(),
                content.getText(),
                content.getVideoUri()
        );
    }
}
