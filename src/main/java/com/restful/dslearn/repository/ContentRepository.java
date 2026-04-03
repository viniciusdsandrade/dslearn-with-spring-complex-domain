package com.restful.dslearn.repository;

import com.restful.dslearn.entity.Content;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentRepository extends JpaRepository<Content, Long> {
}
