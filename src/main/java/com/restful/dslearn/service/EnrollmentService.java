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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final UserRepository userRepository;
    private final OfferRepository offerRepository;

    public EnrollmentService(EnrollmentRepository enrollmentRepository,
                            UserRepository userRepository,
                            OfferRepository offerRepository) {
        this.enrollmentRepository = enrollmentRepository;
        this.userRepository = userRepository;
        this.offerRepository = offerRepository;
    }

    public List<EnrollmentDTO> findAll() {
        return enrollmentRepository.findAll().stream()
                .map(this::toDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public EnrollmentDTO findById(Long userId, Long offerId) {
        Enrollment enrollment = getByCompositeId(userId, offerId);
        return toDTO(enrollment);
    }

    @Transactional
    public EnrollmentDTO create(EnrollmentRequestDTO request) {
        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        Offer offer = offerRepository.findById(request.offerId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Offer not found"));
        EnrollmentPK id = new EnrollmentPK();
        id.setUser(user);
        id.setOffer(offer);
        if (enrollmentRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Enrollment already exists");
        }

        Enrollment enrollment = new Enrollment();
        enrollment.setId(id);
        enrollment.setAvailable(request.available());
        enrollment.setOnlyUpdate(request.onlyUpdate());
        return toDTO(enrollmentRepository.save(enrollment));
    }

    @Transactional
    public EnrollmentDTO update(Long userId, Long offerId, EnrollmentRequestDTO request) {
        EnrollmentPK id = buildId(userId, offerId);
        Enrollment enrollment = enrollmentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Enrollment not found"));
        enrollment.setAvailable(request.available());
        enrollment.setOnlyUpdate(request.onlyUpdate());
        return toDTO(enrollment);
    }

    @Transactional
    public void delete(Long userId, Long offerId) {
        EnrollmentPK id = buildId(userId, offerId);
        if (!enrollmentRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Enrollment not found");
        }
        enrollmentRepository.deleteById(id);
    }

    private EnrollmentDTO toDTO(Enrollment enrollment) {
        return new EnrollmentDTO(
                enrollment.getId().getUser().getId(),
                enrollment.getId().getOffer().getId(),
                enrollment.getAvailable(),
                enrollment.getOnlyUpdate()
        );
    }

    private Enrollment getByCompositeId(Long userId, Long offerId) {
        return enrollmentRepository.findById(buildId(userId, offerId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Enrollment not found"));
    }

    private EnrollmentPK buildId(Long userId, Long offerId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        Offer offer = offerRepository.findById(offerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Offer not found"));
        EnrollmentPK id = new EnrollmentPK();
        id.setUser(user);
        id.setOffer(offer);
        return id;
    }
}
