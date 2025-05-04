package com.irex.CourseService.service;

import com.irex.CourseService.entity.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {
    @Autowired
    private CourseServiceRepository repository;

    public Course findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public List<Course> findByName(String name) {
        return repository.findByName(name);
    }

    public List<Course> findByDepartmentId(Long id) {
        return repository.findByDepartmentId(id);
    }

    public List<Course> findAll() {
        return repository.findAll();
    }

    public List<Course> findByIdIn(List<Long> ids) {
        return repository.findByIdIn(ids);
    }

    public List<Course> findByNameIn(List<String> names) {
        return repository.findByNameIn(names);
    }

    public Course create(Course c) {
        return repository.save(c);
    }

}
