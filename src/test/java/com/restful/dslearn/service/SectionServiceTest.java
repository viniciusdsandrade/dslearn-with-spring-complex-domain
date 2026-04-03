package com.restful.dslearn.service;

import com.restful.dslearn.dto.SectionDTO;
import com.restful.dslearn.dto.SectionRequestDTO;
import com.restful.dslearn.entity.Resource;
import com.restful.dslearn.entity.Section;
import com.restful.dslearn.repository.ResourceRepository;
import com.restful.dslearn.repository.SectionRepository;
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
class SectionServiceTest {

    @Mock
    private SectionRepository sectionRepository;

    @Mock
    private ResourceRepository resourceRepository;

    @InjectMocks
    private SectionService sectionService;

    @Test
    void shouldReturnAllSections() {
        Resource resource = new Resource();
        resource.setId(1L);
        Section section = new Section(1L, "Getting Started", "desc", 1, null, resource, null);
        when(sectionRepository.findAll()).thenReturn(List.of(section));

        List<SectionDTO> result = sectionService.findAll();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).title()).isEqualTo("Getting Started");
        assertThat(result.get(0).prerequisiteId()).isNull();
    }

    @Test
    void shouldReturnSectionWithPrerequisite() {
        Resource resource = new Resource();
        resource.setId(1L);
        Section prereq = new Section(1L, "Prereq", "desc", 1, null, resource, null);
        Section section = new Section(2L, "Advanced", "desc", 2, null, resource, prereq);
        when(sectionRepository.findById(2L)).thenReturn(Optional.of(section));

        SectionDTO result = sectionService.findById(2L);

        assertThat(result.prerequisiteId()).isEqualTo(1L);
    }

    @Test
    void shouldThrowNotFoundWhenSectionMissing() {
        when(sectionRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> sectionService.findById(99L))
                .isInstanceOf(ResponseStatusException.class)
                .extracting(ex -> ((ResponseStatusException) ex).getStatusCode())
                .isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void shouldCreateSectionWithoutPrerequisite() {
        Resource resource = new Resource();
        resource.setId(1L);
        when(resourceRepository.findById(1L)).thenReturn(Optional.of(resource));
        when(sectionRepository.save(any(Section.class))).thenAnswer(inv -> {
            Section saved = inv.getArgument(0);
            saved.setId(5L);
            return saved;
        });

        SectionDTO result = sectionService.create(
                new SectionRequestDTO("Intro", "desc", 1, null, 1L, null));

        assertThat(result.id()).isEqualTo(5L);
        assertThat(result.prerequisiteId()).isNull();
    }

    @Test
    void shouldCreateSectionWithPrerequisite() {
        Resource resource = new Resource();
        resource.setId(1L);
        Section prereq = new Section();
        prereq.setId(1L);
        when(resourceRepository.findById(1L)).thenReturn(Optional.of(resource));
        when(sectionRepository.findById(1L)).thenReturn(Optional.of(prereq));
        when(sectionRepository.save(any(Section.class))).thenAnswer(inv -> {
            Section saved = inv.getArgument(0);
            saved.setId(2L);
            return saved;
        });

        SectionDTO result = sectionService.create(
                new SectionRequestDTO("Advanced", "desc", 2, null, 1L, 1L));

        assertThat(result.prerequisiteId()).isEqualTo(1L);
    }

    @Test
    void shouldUpdateSectionClearingPrerequisite() {
        Resource resource = new Resource();
        resource.setId(1L);
        Section prereq = new Section();
        prereq.setId(1L);
        Section section = new Section(2L, "Advanced", "desc", 2, null, resource, prereq);
        when(sectionRepository.findById(2L)).thenReturn(Optional.of(section));
        when(resourceRepository.findById(1L)).thenReturn(Optional.of(resource));

        SectionDTO result = sectionService.update(2L,
                new SectionRequestDTO("Updated", "new desc", 1, null, 1L, null));

        assertThat(result.title()).isEqualTo("Updated");
        assertThat(result.prerequisiteId()).isNull();
    }

    @Test
    void shouldDeleteSection() {
        when(sectionRepository.existsById(1L)).thenReturn(true);

        sectionService.delete(1L);

        verify(sectionRepository).deleteById(1L);
    }

    @Test
    void shouldThrowNotFoundWhenDeletingMissingSection() {
        when(sectionRepository.existsById(1L)).thenReturn(false);

        assertThatThrownBy(() -> sectionService.delete(1L))
                .isInstanceOf(ResponseStatusException.class)
                .extracting(ex -> ((ResponseStatusException) ex).getStatusCode())
                .isEqualTo(HttpStatus.NOT_FOUND);
    }
}
