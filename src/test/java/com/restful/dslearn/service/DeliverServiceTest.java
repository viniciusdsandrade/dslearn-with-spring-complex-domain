package com.restful.dslearn.service;

import com.restful.dslearn.dto.DeliverDTO;
import com.restful.dslearn.dto.DeliverRequestDTO;
import com.restful.dslearn.entity.*;
import com.restful.dslearn.repository.*;
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
class DeliverServiceTest {

    @Mock private DeliverRepository deliverRepository;
    @Mock private LessonRepository lessonRepository;
    @Mock private EnrollmentRepository enrollmentRepository;
    @Mock private UserRepository userRepository;
    @Mock private OfferRepository offerRepository;

    @InjectMocks
    private DeliverService deliverService;

    private Enrollment buildEnrollment(Long userId, Long offerId) {
        User user = new User();
        user.setId(userId);
        Offer offer = new Offer();
        offer.setId(offerId);
        EnrollmentPK pk = new EnrollmentPK();
        pk.setUser(user);
        pk.setOffer(offer);
        Enrollment enrollment = new Enrollment();
        enrollment.setId(pk);
        return enrollment;
    }

    @Test
    void shouldReturnAllDelivers() {
        Content lesson = new Content();
        lesson.setId(1L);
        Enrollment enrollment = buildEnrollment(1L, 1L);
        Deliver deliver = new Deliver();
        deliver.setId(1L);
        deliver.setUri("gist.com/1");
        deliver.setStatus(DeliverStatus.ACCEPTED);
        deliver.setLesson(lesson);
        deliver.setEnrollment(enrollment);
        when(deliverRepository.findAll()).thenReturn(List.of(deliver));

        List<DeliverDTO> result = deliverService.findAll();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).status()).isEqualTo(DeliverStatus.ACCEPTED);
    }

    @Test
    void shouldFindDeliverById() {
        Content lesson = new Content();
        lesson.setId(1L);
        Enrollment enrollment = buildEnrollment(1L, 2L);
        Deliver deliver = new Deliver();
        deliver.setId(1L);
        deliver.setUri("gist.com/2");
        deliver.setStatus(DeliverStatus.PENDING);
        deliver.setLesson(lesson);
        deliver.setEnrollment(enrollment);
        when(deliverRepository.findById(1L)).thenReturn(Optional.of(deliver));

        DeliverDTO result = deliverService.findById(1L);

        assertThat(result.enrollmentOfferId()).isEqualTo(2L);
    }

    @Test
    void shouldThrowNotFoundWhenDeliverMissing() {
        when(deliverRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> deliverService.findById(99L))
                .isInstanceOf(ResponseStatusException.class)
                .extracting(ex -> ((ResponseStatusException) ex).getStatusCode())
                .isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void shouldCreateDeliver() {
        Content lesson = new Content();
        lesson.setId(1L);
        User user = new User();
        user.setId(1L);
        Offer offer = new Offer();
        offer.setId(2L);
        Enrollment enrollment = buildEnrollment(1L, 2L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(offerRepository.findById(2L)).thenReturn(Optional.of(offer));
        when(lessonRepository.findById(1L)).thenReturn(Optional.of(lesson));
        when(enrollmentRepository.findById(any(EnrollmentPK.class))).thenReturn(Optional.of(enrollment));
        when(deliverRepository.save(any(Deliver.class))).thenAnswer(inv -> {
            Deliver saved = inv.getArgument(0);
            saved.setId(10L);
            return saved;
        });

        DeliverDTO result = deliverService.create(
                new DeliverRequestDTO("gist.com/3", "Good", 8, DeliverStatus.ACCEPTED, 1L, 1L, 2L));

        assertThat(result.id()).isEqualTo(10L);
        assertThat(result.status()).isEqualTo(DeliverStatus.ACCEPTED);
        verify(deliverRepository).save(any(Deliver.class));
    }

    @Test
    void shouldUpdateDeliver() {
        Content lesson = new Content();
        lesson.setId(1L);
        User user = new User();
        user.setId(1L);
        Offer offer = new Offer();
        offer.setId(2L);
        Enrollment enrollment = buildEnrollment(1L, 2L);
        Deliver deliver = new Deliver();
        deliver.setId(1L);
        deliver.setLesson(lesson);
        deliver.setEnrollment(enrollment);

        when(deliverRepository.findById(1L)).thenReturn(Optional.of(deliver));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(offerRepository.findById(2L)).thenReturn(Optional.of(offer));
        when(lessonRepository.findById(1L)).thenReturn(Optional.of(lesson));
        when(enrollmentRepository.findById(any(EnrollmentPK.class))).thenReturn(Optional.of(enrollment));

        DeliverDTO result = deliverService.update(1L,
                new DeliverRequestDTO("gist.com/4", "Updated", 9, DeliverStatus.REJECTED, 1L, 1L, 2L));

        assertThat(result.feedback()).isEqualTo("Updated");
        assertThat(result.status()).isEqualTo(DeliverStatus.REJECTED);
    }

    @Test
    void shouldDeleteDeliver() {
        when(deliverRepository.existsById(1L)).thenReturn(true);

        deliverService.delete(1L);

        verify(deliverRepository).deleteById(1L);
    }

    @Test
    void shouldThrowNotFoundWhenDeletingMissingDeliver() {
        when(deliverRepository.existsById(1L)).thenReturn(false);

        assertThatThrownBy(() -> deliverService.delete(1L))
                .isInstanceOf(ResponseStatusException.class)
                .extracting(ex -> ((ResponseStatusException) ex).getStatusCode())
                .isEqualTo(HttpStatus.NOT_FOUND);
    }
}
