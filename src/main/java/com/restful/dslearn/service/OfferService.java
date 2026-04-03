package com.restful.dslearn.service;

import com.restful.dslearn.dto.OfferDTO;
import com.restful.dslearn.dto.OfferRequestDTO;
import com.restful.dslearn.entity.Course;
import com.restful.dslearn.entity.Offer;
import com.restful.dslearn.repository.CourseRepository;
import com.restful.dslearn.repository.OfferRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OfferService {

    private final OfferRepository offerRepository;
    private final CourseRepository courseRepository;

    public OfferService(OfferRepository offerRepository, CourseRepository courseRepository) {
        this.offerRepository = offerRepository;
        this.courseRepository = courseRepository;
    }

    public List<OfferDTO> findAll() {
        return offerRepository.findAll().stream()
                .map(this::toDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public OfferDTO findById(Long id) {
        Offer offer = offerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Offer not found"));
        return toDTO(offer);
    }

    @Transactional
    public OfferDTO create(OfferRequestDTO request) {
        Course course = courseRepository.findById(request.courseId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found"));

        Offer offer = new Offer();
        offer.setEdition(request.edition());
        offer.setStartMoment(request.startMoment());
        offer.setEndMoment(request.endMoment());
        offer.setCourse(course);
        return toDTO(offerRepository.save(offer));
    }

    @Transactional
    public OfferDTO update(Long id, OfferRequestDTO request) {
        Offer offer = offerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Offer not found"));
        Course course = courseRepository.findById(request.courseId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found"));

        offer.setEdition(request.edition());
        offer.setStartMoment(request.startMoment());
        offer.setEndMoment(request.endMoment());
        offer.setCourse(course);
        return toDTO(offer);
    }

    @Transactional
    public void delete(Long id) {
        if (!offerRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Offer not found");
        }
        offerRepository.deleteById(id);
    }

    private OfferDTO toDTO(Offer offer) {
        return new OfferDTO(
                offer.getId(),
                offer.getEdition(),
                offer.getStartMoment(),
                offer.getEndMoment(),
                offer.getCourse().getId()
        );
    }
}
