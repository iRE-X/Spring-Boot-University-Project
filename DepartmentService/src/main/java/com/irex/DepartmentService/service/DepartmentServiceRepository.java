package com.irex.DepartmentService.service;

import com.irex.DepartmentService.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DepartmentServiceRepository extends JpaRepository<Department, Long> {
    Optional<Department> findByName(String name);
}
