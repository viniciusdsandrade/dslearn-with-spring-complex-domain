package com.restful.dslearn.repository;

import com.restful.dslearn.entity.Section;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SectionRepository extends JpaRepository<Section, Long> {
}
