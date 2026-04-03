package com.restful.dslearn.service;

import com.restful.dslearn.dto.CourseDTO;
import com.restful.dslearn.dto.CourseRequestDTO;
import com.restful.dslearn.entity.Course;
import com.restful.dslearn.repository.CourseRepository;
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
class CourseServiceTest {

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private CourseService courseService;

    @Test
    void shouldReturnAllCoursesAsDTOs() {
        Course spring = new Course(1L, "Spring", "spring.png", "spring-gray.png");
        Course java = new Course(2L, "Java", "java.png", "java-gray.png");
        when(courseRepository.findAll()).thenReturn(List.of(spring, java));

        List<CourseDTO> result = courseService.findAll();

        assertThat(result).hasSize(2);
        assertThat(result.get(0))
                .extracting(CourseDTO::id, CourseDTO::name, CourseDTO::imgUri)
                .containsExactly(1L, "Spring", "spring.png");
        assertThat(result.get(1))
                .extracting(CourseDTO::id, CourseDTO::name, CourseDTO::imgUri)
                .containsExactly(2L, "Java", "java.png");
    }

    @Test
    void shouldFindCourseById() {
        Course course = new Course(10L, "Arquitetura", "img.png", "gray.png");
        when(courseRepository.findById(10L)).thenReturn(Optional.of(course));

        CourseDTO result = courseService.findById(10L);

        assertThat(result.id()).isEqualTo(10L);
        assertThat(result.name()).isEqualTo("Arquitetura");
    }

    @Test
    void shouldThrowNotFoundWhenCourseMissingById() {
        when(courseRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> courseService.findById(99L))
                .isInstanceOf(ResponseStatusException.class)
                .extracting(ex -> ((ResponseStatusException) ex).getStatusCode())
                .isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void shouldCreateCourseAndPersist() {
        CourseRequestDTO request = new CourseRequestDTO("Node", "node.png", "node-gray.png");
        when(courseRepository.save(any(Course.class))).thenAnswer(invocation -> {
            Course savedCourse = invocation.getArgument(0);
            savedCourse.setId(3L);
            return savedCourse;
        });

        CourseDTO result = courseService.create(request);

        assertThat(result).isEqualTo(new CourseDTO(3L, "Node", "node.png", "node-gray.png"));
        verify(courseRepository).save(any(Course.class));
    }

    @Test
    void shouldUpdateCourseWhenExists() {
        Course existing = new Course(7L, "Old", "old.png", "old-gray.png");
        when(courseRepository.findById(7L)).thenReturn(Optional.of(existing));
        CourseRequestDTO request = new CourseRequestDTO("New", "new.png", "new-gray.png");

        CourseDTO result = courseService.update(7L, request);

        assertThat(result.id()).isEqualTo(7L);
        assertThat(result.name()).isEqualTo("New");
        assertThat(result.imgUri()).isEqualTo("new.png");
    }

    @Test
    void shouldDeleteExistingCourse() {
        when(courseRepository.existsById(1L)).thenReturn(true);

        courseService.delete(1L);

        verify(courseRepository).deleteById(1L);
    }

    @Test
    void shouldThrowNotFoundWhenDeletingMissingCourse() {
        when(courseRepository.existsById(2L)).thenReturn(false);

        assertThatThrownBy(() -> courseService.delete(2L))
                .isInstanceOf(ResponseStatusException.class)
                .extracting(ex -> ((ResponseStatusException) ex).getStatusCode())
                .isEqualTo(HttpStatus.NOT_FOUND);
    }
}
