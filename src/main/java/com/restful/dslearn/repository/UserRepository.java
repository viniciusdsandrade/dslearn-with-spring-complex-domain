package com.restful.dslearn.repository;

import com.restful.dslearn.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
