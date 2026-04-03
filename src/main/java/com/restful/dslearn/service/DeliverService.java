package com.restful.dslearn.service;

import com.restful.dslearn.dto.DeliverDTO;
import com.restful.dslearn.dto.DeliverRequestDTO;
import com.restful.dslearn.entity.Deliver;
import com.restful.dslearn.entity.Enrollment;
import com.restful.dslearn.entity.EnrollmentPK;
import com.restful.dslearn.entity.Lesson;
import com.restful.dslearn.entity.Offer;
import com.restful.dslearn.entity.User;
import com.restful.dslearn.repository.DeliverRepository;
import com.restful.dslearn.repository.OfferRepository;
import com.restful.dslearn.repository.EnrollmentRepository;
import com.restful.dslearn.repository.LessonRepository;
import com.restful.dslearn.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class DeliverService {

    private final DeliverRepository deliverRepository;
    private final LessonRepository lessonRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final UserRepository userRepository;
    private final OfferRepository offerRepository;

    public DeliverService(DeliverRepository deliverRepository,
                          LessonRepository lessonRepository,
                          EnrollmentRepository enrollmentRepository,
                          UserRepository userRepository,
                          OfferRepository offerRepository) {
        this.deliverRepository = deliverRepository;
        this.lessonRepository = lessonRepository;
        this.enrollmentRepository = enrollmentRepository;
        this.userRepository = userRepository;
        this.offerRepository = offerRepository;
    }

    public List<DeliverDTO> findAll() {
        return deliverRepository.findAll().stream()
                .map(this::toDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public DeliverDTO findById(Long id) {
        Deliver deliver = deliverRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Deliver not found"));
        return toDTO(deliver);
    }

    @Transactional
    public DeliverDTO create(DeliverRequestDTO request) {
        Lesson lesson = lessonRepository.findById(request.lessonId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Lesson not found"));
        Enrollment enrollment = enrollmentRepository.findById(buildEnrollmentId(request.enrollmentUserId(), request.enrollmentOfferId()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Enrollment not found"));

        Deliver deliver = new Deliver();
        deliver.setUri(request.uri());
        deliver.setFeedback(request.feedback());
        deliver.setCorrectCount(request.correctCount());
        deliver.setStatus(request.status());
        deliver.setLesson(lesson);
        deliver.setEnrollment(enrollment);
        return toDTO(deliverRepository.save(deliver));
    }

    @Transactional
    public DeliverDTO update(Long id, DeliverRequestDTO request) {
        Deliver deliver = deliverRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Deliver not found"));
        Lesson lesson = lessonRepository.findById(request.lessonId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Lesson not found"));
        Enrollment enrollment = enrollmentRepository.findById(buildEnrollmentId(request.enrollmentUserId(), request.enrollmentOfferId()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Enrollment not found"));

        deliver.setUri(request.uri());
        deliver.setFeedback(request.feedback());
        deliver.setCorrectCount(request.correctCount());
        deliver.setStatus(request.status());
        deliver.setLesson(lesson);
        deliver.setEnrollment(enrollment);
        return toDTO(deliver);
    }

    @Transactional
    public void delete(Long id) {
        if (!deliverRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Deliver not found");
        }
        deliverRepository.deleteById(id);
    }

    private DeliverDTO toDTO(Deliver deliver) {
        return new DeliverDTO(
                deliver.getId(),
                deliver.getUri(),
                deliver.getFeedback(),
                deliver.getCorrectCount(),
                deliver.getStatus(),
                deliver.getLesson().getId(),
                deliver.getEnrollment().getId().getUser().getId(),
                deliver.getEnrollment().getId().getOffer().getId()
        );
    }

    private EnrollmentPK buildEnrollmentId(Long userId, Long offerId) {
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
