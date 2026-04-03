package com.restful.dslearn.repository;

import com.restful.dslearn.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
}
