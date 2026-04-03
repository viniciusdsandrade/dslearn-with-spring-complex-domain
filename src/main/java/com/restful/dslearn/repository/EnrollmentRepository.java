package com.restful.dslearn.repository;

import com.restful.dslearn.entity.Enrollment;
import com.restful.dslearn.entity.EnrollmentPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnrollmentRepository extends JpaRepository<Enrollment, EnrollmentPK> {
}
