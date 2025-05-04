package com.irex.StudentService.service;

import com.irex.StudentService.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface StudentServiceRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByRollNo(Long rollNo);
    List<Student> findByName(String name);
    List<Student> findByDepartmentId(Long departmentId);
}
