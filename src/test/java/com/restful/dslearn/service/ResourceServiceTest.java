package com.restful.dslearn.service;

import com.restful.dslearn.dto.ResourceDTO;
import com.restful.dslearn.dto.ResourceRequestDTO;
import com.restful.dslearn.entity.Offer;
import com.restful.dslearn.entity.Resource;
import com.restful.dslearn.entity.ResourceType;
import com.restful.dslearn.repository.OfferRepository;
import com.restful.dslearn.repository.ResourceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ResourceServiceTest {

    @Mock
    private ResourceRepository resourceRepository;

    @Mock
    private OfferRepository offerRepository;

    @InjectMocks
    private ResourceService resourceService;

    @Test
    void shouldReturnAllResources() {
        Offer offer = new Offer();
        offer.setId(1L);
        Resource resource = new Resource();
        resource.setId(1L);
        resource.setTitle("Intro");
        resource.setType(ResourceType.LESSON_ONLY);
        resource.setOffer(offer);
        when(resourceRepository.findAll()).thenReturn(List.of(resource));

        List<ResourceDTO> result = resourceService.findAll();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).title()).isEqualTo("Intro");
    }

    @Test
    void shouldFindResourceById() {
        Offer offer = new Offer();
        offer.setId(1L);
        Resource resource = new Resource();
        resource.setId(1L);
        resource.setTitle("Forum");
        resource.setType(ResourceType.FORUM);
        resource.setOffer(offer);
        when(resourceRepository.findById(1L)).thenReturn(Optional.of(resource));

        ResourceDTO result = resourceService.findById(1L);

        assertThat(result.type()).isEqualTo(ResourceType.FORUM);
    }

    @Test
    void shouldThrowNotFoundWhenResourceMissing() {
        when(resourceRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> resourceService.findById(99L))
                .isInstanceOf(ResponseStatusException.class)
                .extracting(ex -> ((ResponseStatusException) ex).getStatusCode())
                .isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void shouldCreateResource() {
        Offer offer = new Offer();
        offer.setId(1L);
        when(offerRepository.findById(1L)).thenReturn(Optional.of(offer));
        when(resourceRepository.save(any(Resource.class))).thenAnswer(inv -> {
            Resource saved = inv.getArgument(0);
            saved.setId(5L);
            return saved;
        });

        ResourceDTO result = resourceService.create(
                new ResourceRequestDTO("Tasks", "desc", 1, null, ResourceType.LESSON_TASK, 1L));

        assertThat(result.id()).isEqualTo(5L);
        assertThat(result.title()).isEqualTo("Tasks");
        verify(resourceRepository).save(any(Resource.class));
    }

    @Test
    void shouldThrowNotFoundWhenOfferMissingOnCreate() {
        when(offerRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> resourceService.create(
                new ResourceRequestDTO("X", null, 1, null, ResourceType.FORUM, 99L)))
                .isInstanceOf(ResponseStatusException.class)
                .extracting(ex -> ((ResponseStatusException) ex).getStatusCode())
                .isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void shouldUpdateResource() {
        Offer offer = new Offer();
        offer.setId(1L);
        Resource resource = new Resource();
        resource.setId(1L);
        resource.setTitle("Old");
        resource.setOffer(offer);
        when(resourceRepository.findById(1L)).thenReturn(Optional.of(resource));
        when(offerRepository.findById(1L)).thenReturn(Optional.of(offer));

        ResourceDTO result = resourceService.update(1L,
                new ResourceRequestDTO("New", "desc", 2, "img.png", ResourceType.EXTERNAL_LINK, 1L));

        assertThat(result.title()).isEqualTo("New");
        assertThat(result.type()).isEqualTo(ResourceType.EXTERNAL_LINK);
    }

    @Test
    void shouldDeleteResource() {
        when(resourceRepository.existsById(1L)).thenReturn(true);

        resourceService.delete(1L);

        verify(resourceRepository).deleteById(1L);
    }

    @Test
    void shouldThrowNotFoundWhenDeletingMissingResource() {
        when(resourceRepository.existsById(1L)).thenReturn(false);

        assertThatThrownBy(() -> resourceService.delete(1L))
                .isInstanceOf(ResponseStatusException.class)
                .extracting(ex -> ((ResponseStatusException) ex).getStatusCode())
                .isEqualTo(HttpStatus.NOT_FOUND);
    }
}
