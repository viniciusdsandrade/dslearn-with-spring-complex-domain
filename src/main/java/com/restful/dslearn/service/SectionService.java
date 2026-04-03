package com.restful.dslearn.service;

import com.restful.dslearn.dto.SectionDTO;
import com.restful.dslearn.dto.SectionRequestDTO;
import com.restful.dslearn.entity.Resource;
import com.restful.dslearn.entity.Section;
import com.restful.dslearn.repository.ResourceRepository;
import com.restful.dslearn.repository.SectionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class SectionService {

    private final SectionRepository sectionRepository;
    private final ResourceRepository resourceRepository;

    public SectionService(SectionRepository sectionRepository, ResourceRepository resourceRepository) {
        this.sectionRepository = sectionRepository;
        this.resourceRepository = resourceRepository;
    }

    public List<SectionDTO> findAll() {
        return sectionRepository.findAll().stream()
                .map(this::toDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public SectionDTO findById(Long id) {
        Section section = sectionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Section not found"));
        return toDTO(section);
    }

    @Transactional
    public SectionDTO create(SectionRequestDTO request) {
        Resource resource = resourceRepository.findById(request.resourceId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource not found"));

        Section section = new Section();
        section.setTitle(request.title());
        section.setDescription(request.description());
        section.setPosition(request.position());
        section.setImgUri(request.imgUri());
        section.setResource(resource);

        if (request.prerequisiteId() != null) {
            Section prerequisite = sectionRepository.findById(request.prerequisiteId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Prerequisite section not found"));
            section.setPrerequisite(prerequisite);
        }

        return toDTO(sectionRepository.save(section));
    }

    @Transactional
    public SectionDTO update(Long id, SectionRequestDTO request) {
        Section section = sectionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Section not found"));
        Resource resource = resourceRepository.findById(request.resourceId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource not found"));

        section.setTitle(request.title());
        section.setDescription(request.description());
        section.setPosition(request.position());
        section.setImgUri(request.imgUri());
        section.setResource(resource);

        if (request.prerequisiteId() != null) {
            Section prerequisite = sectionRepository.findById(request.prerequisiteId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Prerequisite section not found"));
            section.setPrerequisite(prerequisite);
        } else {
            section.setPrerequisite(null);
        }
        return toDTO(section);
    }

    @Transactional
    public void delete(Long id) {
        if (!sectionRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Section not found");
        }
        sectionRepository.deleteById(id);
    }

    private SectionDTO toDTO(Section section) {
        return new SectionDTO(
                section.getId(),
                section.getTitle(),
                section.getDescription(),
                section.getPosition(),
                section.getImgUri(),
                section.getResource().getId(),
                section.getPrerequisite() != null ? section.getPrerequisite().getId() : null
        );
    }
}
