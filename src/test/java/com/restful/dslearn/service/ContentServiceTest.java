package com.restful.dslearn.service;

import com.restful.dslearn.dto.ContentDTO;
import com.restful.dslearn.dto.ContentRequestDTO;
import com.restful.dslearn.entity.Content;
import com.restful.dslearn.entity.Section;
import com.restful.dslearn.repository.ContentRepository;
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
class ContentServiceTest {

    @Mock
    private ContentRepository contentRepository;

    @Mock
    private SectionRepository sectionRepository;

    @InjectMocks
    private ContentService contentService;

    @Test
    void shouldReturnAllContents() {
        Section section = new Section();
        section.setId(1L);
        Content content = new Content();
        content.setId(1L);
        content.setTitle("Intro");
        content.setPosition(1);
        content.setSection(section);
        content.setText("Welcome");
        content.setVideoUri("video.mp4");
        when(contentRepository.findAll()).thenReturn(List.of(content));

        List<ContentDTO> result = contentService.findAll();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).title()).isEqualTo("Intro");
    }

    @Test
    void shouldFindContentById() {
        Section section = new Section();
        section.setId(1L);
        Content content = new Content();
        content.setId(1L);
        content.setTitle("OOP");
        content.setPosition(1);
        content.setSection(section);
        content.setText("Classes");
        content.setVideoUri("oop.mp4");
        when(contentRepository.findById(1L)).thenReturn(Optional.of(content));

        ContentDTO result = contentService.findById(1L);

        assertThat(result.text()).isEqualTo("Classes");
    }

    @Test
    void shouldThrowNotFoundWhenContentMissing() {
        when(contentRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> contentService.findById(99L))
                .isInstanceOf(ResponseStatusException.class)
                .extracting(ex -> ((ResponseStatusException) ex).getStatusCode())
                .isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void shouldCreateContent() {
        Section section = new Section();
        section.setId(1L);
        when(sectionRepository.findById(1L)).thenReturn(Optional.of(section));
        when(contentRepository.save(any(Content.class))).thenAnswer(inv -> {
            Content saved = inv.getArgument(0);
            saved.setId(10L);
            return saved;
        });

        ContentDTO result = contentService.create(
                new ContentRequestDTO("New", 1, 1L, "text", "uri.mp4"));

        assertThat(result.id()).isEqualTo(10L);
        assertThat(result.title()).isEqualTo("New");
        verify(contentRepository).save(any(Content.class));
    }

    @Test
    void shouldThrowNotFoundWhenSectionMissingOnCreate() {
        when(sectionRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> contentService.create(
                new ContentRequestDTO("X", 1, 99L, "t", "v")))
                .isInstanceOf(ResponseStatusException.class)
                .extracting(ex -> ((ResponseStatusException) ex).getStatusCode())
                .isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void shouldUpdateContent() {
        Section section = new Section();
        section.setId(1L);
        Content content = new Content();
        content.setId(1L);
        content.setTitle("Old");
        content.setSection(section);
        when(contentRepository.findById(1L)).thenReturn(Optional.of(content));
        when(sectionRepository.findById(1L)).thenReturn(Optional.of(section));

        ContentDTO result = contentService.update(1L,
                new ContentRequestDTO("Updated", 2, 1L, "new text", "new.mp4"));

        assertThat(result.title()).isEqualTo("Updated");
        assertThat(result.videoUri()).isEqualTo("new.mp4");
    }

    @Test
    void shouldDeleteContent() {
        when(contentRepository.existsById(1L)).thenReturn(true);

        contentService.delete(1L);

        verify(contentRepository).deleteById(1L);
    }

    @Test
    void shouldThrowNotFoundWhenDeletingMissingContent() {
        when(contentRepository.existsById(1L)).thenReturn(false);

        assertThatThrownBy(() -> contentService.delete(1L))
                .isInstanceOf(ResponseStatusException.class)
                .extracting(ex -> ((ResponseStatusException) ex).getStatusCode())
                .isEqualTo(HttpStatus.NOT_FOUND);
    }
}
