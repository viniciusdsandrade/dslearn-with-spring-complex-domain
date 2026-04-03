package com.restful.dslearn.repository;

import com.restful.dslearn.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
