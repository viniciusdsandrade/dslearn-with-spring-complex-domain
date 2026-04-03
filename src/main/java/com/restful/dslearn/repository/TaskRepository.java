package com.restful.dslearn.repository;

import com.restful.dslearn.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
