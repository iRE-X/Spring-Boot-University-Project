package com.irex.CourseService.service;

import com.irex.CourseService.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CourseServiceRepository extends JpaRepository<Course, Long> {
    List<Course> findByName(String name);
    List<Course> findByDepartmentId(Long id);
    List<Course> findByIdIn(List<Long> ids);
    List<Course> findByNameIn(List<String> names);
}
