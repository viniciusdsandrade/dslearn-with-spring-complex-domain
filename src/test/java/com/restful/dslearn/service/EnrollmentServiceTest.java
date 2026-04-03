package com.restful.dslearn.service;

import com.restful.dslearn.dto.EnrollmentDTO;
import com.restful.dslearn.dto.EnrollmentRequestDTO;
import com.restful.dslearn.entity.Enrollment;
import com.restful.dslearn.entity.EnrollmentPK;
import com.restful.dslearn.entity.Offer;
import com.restful.dslearn.entity.User;
import com.restful.dslearn.repository.EnrollmentRepository;
import com.restful.dslearn.repository.OfferRepository;
import com.restful.dslearn.repository.UserRepository;
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
class EnrollmentServiceTest {

    @Mock
    private EnrollmentRepository enrollmentRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private OfferRepository offerRepository;

    @InjectMocks
    private EnrollmentService enrollmentService;

    @Test
    void shouldFindAllEnrollments() {
        User user = new User();
        user.setId(1L);
        Offer offer = new Offer();
        offer.setId(2L);

        EnrollmentPK id = new EnrollmentPK();
        id.setUser(user);
        id.setOffer(offer);

        Enrollment enrollment = new Enrollment();
        enrollment.setId(id);
        enrollment.setAvailable(true);
        enrollment.setOnlyUpdate(false);
        when(enrollmentRepository.findAll()).thenReturn(List.of(enrollment));

        List<EnrollmentDTO> result = enrollmentService.findAll();

        assertThat(result).hasSize(1);
        assertThat(result.get(0))
                .extracting(EnrollmentDTO::userId, EnrollmentDTO::offerId, EnrollmentDTO::available, EnrollmentDTO::onlyUpdate)
                .containsExactly(1L, 2L, true, false);
    }

    @Test
    void shouldFindEnrollmentByCompositeId() {
        Enrollment enrollment = buildEnrollment(1L, 2L, true, false);

        when(userRepository.findById(1L)).thenReturn(Optional.of(enrollment.getId().getUser()));
        when(offerRepository.findById(2L)).thenReturn(Optional.of(enrollment.getId().getOffer()));
        when(enrollmentRepository.findById(any(EnrollmentPK.class))).thenReturn(Optional.of(enrollment));

        EnrollmentDTO result = enrollmentService.findById(1L, 2L);

        assertThat(result.userId()).isEqualTo(1L);
        assertThat(result.offerId()).isEqualTo(2L);
    }

    @Test
    void shouldCreateEnrollmentWhenRelationsExist() {
        EnrollmentRequestDTO request = new EnrollmentRequestDTO(1L, 2L, true, false);
        User user = new User();
        user.setId(1L);
        Offer offer = new Offer();
        offer.setId(2L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(offerRepository.findById(2L)).thenReturn(Optional.of(offer));
        when(enrollmentRepository.existsById(any(EnrollmentPK.class))).thenReturn(false);
        when(enrollmentRepository.save(any(Enrollment.class))).thenAnswer(invocation -> invocation.getArgument(0));

        EnrollmentDTO result = enrollmentService.create(request);

        assertThat(result).isEqualTo(new EnrollmentDTO(1L, 2L, true, false));
        verify(enrollmentRepository).save(any(Enrollment.class));
    }

    @Test
    void shouldThrowConflictWhenEnrollmentAlreadyExists() {
        EnrollmentRequestDTO request = new EnrollmentRequestDTO(1L, 2L, true, false);
        User user = new User();
        user.setId(1L);
        Offer offer = new Offer();
        offer.setId(2L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(offerRepository.findById(2L)).thenReturn(Optional.of(offer));
        when(enrollmentRepository.existsById(any(EnrollmentPK.class))).thenReturn(true);

        assertThatThrownBy(() -> enrollmentService.create(request))
                .isInstanceOf(ResponseStatusException.class)
                .extracting(ex -> ((ResponseStatusException) ex).getStatusCode())
                .isEqualTo(HttpStatus.CONFLICT);
    }

    @Test
    void shouldUpdateEnrollmentWhenExists() {
        User user = new User();
        user.setId(1L);
        Offer offer = new Offer();
        offer.setId(2L);
        EnrollmentPK id = new EnrollmentPK();
        id.setUser(user);
        id.setOffer(offer);
        Enrollment enrollment = new Enrollment();
        enrollment.setId(id);
        enrollment.setAvailable(false);
        enrollment.setOnlyUpdate(false);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(offerRepository.findById(2L)).thenReturn(Optional.of(offer));
        when(enrollmentRepository.findById(any(EnrollmentPK.class))).thenReturn(Optional.of(enrollment));

        EnrollmentDTO result = enrollmentService.update(1L, 2L, new EnrollmentRequestDTO(1L, 2L, true, true));

        assertThat(result.available()).isTrue();
        assertThat(result.onlyUpdate()).isTrue();
    }

    @Test
    void shouldDeleteEnrollmentWhenExists() {
        User user = new User();
        user.setId(1L);
        Offer offer = new Offer();
        offer.setId(2L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(offerRepository.findById(2L)).thenReturn(Optional.of(offer));
        when(enrollmentRepository.existsById(any(EnrollmentPK.class))).thenReturn(true);

        enrollmentService.delete(1L, 2L);

        verify(enrollmentRepository).deleteById(any(EnrollmentPK.class));
    }

    private Enrollment buildEnrollment(Long userId, Long offerId, Boolean available, Boolean onlyUpdate) {
        User user = new User();
        user.setId(userId);
        Offer offer = new Offer();
        offer.setId(offerId);

        EnrollmentPK id = new EnrollmentPK();
        id.setUser(user);
        id.setOffer(offer);

        Enrollment enrollment = new Enrollment();
        enrollment.setId(id);
        enrollment.setAvailable(available);
        enrollment.setOnlyUpdate(onlyUpdate);
        return enrollment;
    }
}
