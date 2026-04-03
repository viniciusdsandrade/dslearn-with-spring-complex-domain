package com.restful.dslearn.service;

import com.restful.dslearn.dto.OfferDTO;
import com.restful.dslearn.dto.OfferRequestDTO;
import com.restful.dslearn.entity.Course;
import com.restful.dslearn.entity.Offer;
import com.restful.dslearn.repository.CourseRepository;
import com.restful.dslearn.repository.OfferRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OfferServiceTest {

    @Mock
    private OfferRepository offerRepository;

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private OfferService offerService;

    private final Instant start = Instant.parse("2025-01-10T09:00:00Z");
    private final Instant end = Instant.parse("2025-03-30T18:00:00Z");

    @Test
    void shouldReturnAllOffers() {
        Course course = new Course(1L, "Java", "j.png", "jg.png");
        Offer offer = new Offer(1L, "2025.1", start, end, course);
        when(offerRepository.findAll()).thenReturn(List.of(offer));

        List<OfferDTO> result = offerService.findAll();

        assertThat(result).hasSize(1);
        assertThat(result.get(0))
                .extracting(OfferDTO::id, OfferDTO::edition, OfferDTO::courseId)
                .containsExactly(1L, "2025.1", 1L);
    }

    @Test
    void shouldFindOfferById() {
        Course course = new Course(1L, "Java", "j.png", "jg.png");
        Offer offer = new Offer(1L, "2025.1", start, end, course);
        when(offerRepository.findById(1L)).thenReturn(Optional.of(offer));

        OfferDTO result = offerService.findById(1L);

        assertThat(result.edition()).isEqualTo("2025.1");
    }

    @Test
    void shouldThrowNotFoundWhenOfferMissing() {
        when(offerRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> offerService.findById(99L))
                .isInstanceOf(ResponseStatusException.class)
                .extracting(ex -> ((ResponseStatusException) ex).getStatusCode())
                .isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void shouldCreateOffer() {
        Course course = new Course(1L, "Java", "j.png", "jg.png");
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        when(offerRepository.save(any(Offer.class))).thenAnswer(inv -> {
            Offer saved = inv.getArgument(0);
            saved.setId(10L);
            return saved;
        });

        OfferDTO result = offerService.create(new OfferRequestDTO("2025.2", start, end, 1L));

        assertThat(result.id()).isEqualTo(10L);
        assertThat(result.edition()).isEqualTo("2025.2");
        verify(offerRepository).save(any(Offer.class));
    }

    @Test
    void shouldThrowNotFoundWhenCourseNotFoundOnCreate() {
        when(courseRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> offerService.create(new OfferRequestDTO("2025.2", start, end, 99L)))
                .isInstanceOf(ResponseStatusException.class)
                .extracting(ex -> ((ResponseStatusException) ex).getStatusCode())
                .isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void shouldUpdateOffer() {
        Course course = new Course(1L, "Java", "j.png", "jg.png");
        Offer offer = new Offer(1L, "2025.1", start, end, course);
        when(offerRepository.findById(1L)).thenReturn(Optional.of(offer));
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));

        OfferDTO result = offerService.update(1L, new OfferRequestDTO("2025.3", start, end, 1L));

        assertThat(result.edition()).isEqualTo("2025.3");
    }

    @Test
    void shouldDeleteOffer() {
        when(offerRepository.existsById(1L)).thenReturn(true);

        offerService.delete(1L);

        verify(offerRepository).deleteById(1L);
    }

    @Test
    void shouldThrowNotFoundWhenDeletingMissingOffer() {
        when(offerRepository.existsById(1L)).thenReturn(false);

        assertThatThrownBy(() -> offerService.delete(1L))
                .isInstanceOf(ResponseStatusException.class)
                .extracting(ex -> ((ResponseStatusException) ex).getStatusCode())
                .isEqualTo(HttpStatus.NOT_FOUND);
    }
}
