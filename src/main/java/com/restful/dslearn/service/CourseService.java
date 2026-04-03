package com.restful.dslearn.service;

import com.restful.dslearn.dto.CourseDTO;
import com.restful.dslearn.dto.CourseRequestDTO;
import com.restful.dslearn.entity.Course;
import com.restful.dslearn.repository.CourseRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<CourseDTO> findAll() {
        return courseRepository.findAll().stream()
                .map(this::toDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public CourseDTO findById(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Course not found")
                );
        return toDTO(course);
    }

    @Transactional
    public CourseDTO create(CourseRequestDTO request) {
        Course course = new Course();
        course.setName(request.name());
        course.setImgUri(request.imgUri());
        course.setImgGrayUri(request.imgGrayUri());
        return toDTO(courseRepository.save(course));
    }

    @Transactional
    public CourseDTO update(Long id, CourseRequestDTO request) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Course not found")
                );
        course.setName(request.name());
        course.setImgUri(request.imgUri());
        course.setImgGrayUri(request.imgGrayUri());
        return toDTO(course);
    }

    @Transactional
    public void delete(Long id) {
        if (!courseRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found");
        }
        courseRepository.deleteById(id);
    }

    private CourseDTO toDTO(Course course) {
        return new CourseDTO(
                course.getId(),
                course.getName(),
                course.getImgUri(),
                course.getImgGrayUri()
        );
    }
}
