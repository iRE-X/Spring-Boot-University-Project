package com.irex.CourseService;

import com.irex.CourseService.entity.Course;
import com.irex.CourseService.record.CourseDTO;
import com.irex.CourseService.record.Department;
import com.irex.CourseService.service.CourseService;
import com.irex.CourseService.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/courses", produces = "application/json")
public class CourseController {
    @Autowired
    private CourseService service;

    @Autowired
    private DepartmentService departmentService;

    @GetMapping
    public ResponseEntity<List<Course>> getCourses(@RequestParam(required = false) String departmentName) {
        List<Course> res = null;

        if(departmentName != null) {
            Department dept = departmentService.findDepartmentByName(departmentName);
            if(dept == null) return null;
            res = service.findByDepartmentId(dept.id());
        } else {
            res = service.findAll();
        }

        if(res == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return ResponseEntity.ok(res);
    }

    @GetMapping(value = "/raw")
    public ResponseEntity<List<Course>> getAllCourses(@RequestParam(required = false) Long departmentId) {
        List<Course> res = null;

        if (departmentId != null) res = service.findByDepartmentId(departmentId);
        else res = service.findAll();

        if(res == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return ResponseEntity.ok(res);
    }

    @GetMapping(value = "/{courseName}")
    public ResponseEntity<List<CourseDTO>> get(@PathVariable("courseName") String name) {
        List<Course> c = service.findByName(name);
        if (c == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return ResponseEntity.ok(batchGenerateDTO(c));
    }

    @PostMapping
    public ResponseEntity<Course> create(@RequestBody CourseDTO courseDTO) {
        String courseName = courseDTO.name();
        String departmentName = courseDTO.departmentName();

        Department department = departmentService.findDepartmentByName(departmentName);
        if(department == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Course course = Course.builder().name(courseName).departmentId(department.id()).build();
        Course c = service.create(course);

        if(c == null) return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        return new ResponseEntity<>(c, HttpStatus.CREATED);
    }

    @PostMapping(value = "/by-ids")
    public ResponseEntity<List<Course>> getByIds(@RequestBody List<Long> ids) {
        List<Course> courses = service.findByIdIn(ids);
        if(courses == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return ResponseEntity.ok(courses);
    }

    @PostMapping(value = "/by-names")
    public ResponseEntity<List<Course>> getByNames(@RequestBody List<String> names) {
        List<Course> courses = service.findByNameIn(names);
        if(courses == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return ResponseEntity.ok(courses);
    }

    private List<CourseDTO> batchGenerateDTO(List<Course> students) {
        List<CourseDTO> res = new ArrayList<>();
        for(Course s : students) res.add(generateDTO(s));
        return res;
    }

    private CourseDTO generateDTO(Course c) {
        if(c == null) return null;
        Department d = departmentService.findDepartmentById(c.getDepartmentId());
        return new CourseDTO(c.getName(), d.name());
    }
}
