package com.irex.DepartmentService;

import com.irex.DepartmentService.entity.Department;
import com.irex.DepartmentService.record.Course;
import com.irex.DepartmentService.record.DepartmentDTO;
import com.irex.DepartmentService.record.Student;
import com.irex.DepartmentService.service.CourseService;
import com.irex.DepartmentService.service.DepartmentService;
import com.irex.DepartmentService.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(value = "/api/departments", produces = "application/json")
public class DepartmentController {
    @Autowired
    private DepartmentService service;

    @Autowired
    private CourseService courseService;

    @Autowired
    private StudentService studentService;

    @GetMapping
    public ResponseEntity<List<DepartmentDTO>> getAll() {
        List<Department> res = service.findAll();
        if(res == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return ResponseEntity.ok(batchGenerateDTO(res));
    }

    @GetMapping(value = "/raw")
    public ResponseEntity<List<Department>> getAllDepartments() {
        List<Department> res = service.findAll();
        if(res == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return ResponseEntity.ok(res);
    }

    @GetMapping(value = "/{departmentName}")
    public ResponseEntity<DepartmentDTO> get(@PathVariable("departmentName") String name) {
        Department d = service.findByName(name);
        if(d == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return ResponseEntity.ok(generateDTO(d));
    }

    @GetMapping(value = "/{departmentName}/{courseName}")
    public ResponseEntity<Course> get(@PathVariable String departmentName, @PathVariable String courseName) {
        Department d = service.findByName(departmentName);
        if(d == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        List<Course> res = courseService.getCoursesByDepartment(d.getId());
        for(Course c : res) {
            if(Objects.equals(c.name(), courseName))
                return ResponseEntity.ok(c);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<Department> create(@RequestBody Department department) {
        if(department.getName() == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        Department d = service.create(department);
        if(d == null) return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(d, HttpStatus.CREATED);
    }


    private List<DepartmentDTO> batchGenerateDTO(List<Department> departments) {
        List<DepartmentDTO> res = new ArrayList<>();
        for(Department d : departments) res.add(generateDTO(d));
        return res;
    }

    private DepartmentDTO generateDTO(Department d) {
        if(d == null) return null;
        List<String> courses = courseService.getCoursesByDepartment(d.getId()).stream().map(Course::name).toList();
        List<Student> students = studentService.getStudentsByDepartment(d.getId());
        return new DepartmentDTO(d.getName(), courses, students);
    }
}
