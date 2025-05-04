package com.irex.DepartmentService.service;

import com.irex.DepartmentService.entity.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {
    @Autowired
    private DepartmentServiceRepository repository;

    public Department findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Department findByName(String name) {
        return repository.findByName(name).orElse(null);
    }

    public List<Department> findAll() {
        return repository.findAll();
    }

    public Department create(Department d) {
        return repository.save(d);
    }
}
