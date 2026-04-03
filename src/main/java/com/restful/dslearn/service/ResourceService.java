package com.restful.dslearn.service;

import com.restful.dslearn.dto.ResourceDTO;
import com.restful.dslearn.dto.ResourceRequestDTO;
import com.restful.dslearn.entity.Offer;
import com.restful.dslearn.entity.Resource;
import com.restful.dslearn.repository.OfferRepository;
import com.restful.dslearn.repository.ResourceRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ResourceService {

    private final ResourceRepository resourceRepository;
    private final OfferRepository offerRepository;

    public ResourceService(ResourceRepository resourceRepository, OfferRepository offerRepository) {
        this.resourceRepository = resourceRepository;
        this.offerRepository = offerRepository;
    }

    public List<ResourceDTO> findAll() {
        return resourceRepository.findAll().stream()
                .map(this::toDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public ResourceDTO findById(Long id) {
        Resource resource = resourceRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource not found"));
        return toDTO(resource);
    }

    @Transactional
    public ResourceDTO create(ResourceRequestDTO request) {
        Offer offer = offerRepository.findById(request.offerId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Offer not found"));
        Resource resource = new Resource();
        resource.setTitle(request.title());
        resource.setDescription(request.description());
        resource.setPosition(request.position());
        resource.setImgUri(request.imgUri());
        resource.setType(request.type());
        resource.setOffer(offer);
        return toDTO(resourceRepository.save(resource));
    }

    @Transactional
    public ResourceDTO update(Long id, ResourceRequestDTO request) {
        Resource resource = resourceRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource not found"));
        Offer offer = offerRepository.findById(request.offerId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Offer not found"));

        resource.setTitle(request.title());
        resource.setDescription(request.description());
        resource.setPosition(request.position());
        resource.setImgUri(request.imgUri());
        resource.setType(request.type());
        resource.setOffer(offer);
        return toDTO(resource);
    }

    @Transactional
    public void delete(Long id) {
        if (!resourceRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource not found");
        }
        resourceRepository.deleteById(id);
    }

    private ResourceDTO toDTO(Resource resource) {
        return new ResourceDTO(
                resource.getId(),
                resource.getTitle(),
                resource.getDescription(),
                resource.getPosition(),
                resource.getImgUri(),
                resource.getType(),
                resource.getOffer().getId()
        );
    }
}
