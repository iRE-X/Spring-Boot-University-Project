package com.irex.StudentService.service;

import com.irex.StudentService.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    @Autowired
    private StudentServiceRepository repository;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private CourseService courseService;

    public Student findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Student findByRollNo(Long rollNo) {
        return repository.findByRollNo(rollNo).orElse(null);
    }

    public List<Student> findByName(String name) {
        return repository.findByName(name);
    }

    public List<Student> findByDepartmentId(Long deptId) {
        return repository.findByDepartmentId(deptId);
    }

    public List<Student> findAll() {
        return repository.findAll();
    }

    public Student save(Student s) {
        return repository.save(s);
    }

    public Student create(Student d) {
        return repository.save(d);
    }
}
