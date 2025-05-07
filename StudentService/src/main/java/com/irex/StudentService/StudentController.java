package com.irex.StudentService;

import com.irex.StudentService.entity.Student;
import com.irex.StudentService.record.Course;
import com.irex.StudentService.record.Department;
import com.irex.StudentService.record.StudentDTO;
import com.irex.StudentService.service.CourseService;
import com.irex.StudentService.service.DepartmentService;
import com.irex.StudentService.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(value = "/api/students")
public class StudentController {
    @Autowired
    private StudentService service;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private CourseService courseService;

    @GetMapping
    public ResponseEntity<List<StudentDTO>> getStudents(@RequestParam(required = false) String name) {
        List<Student> res = null;

        if(name != null) res = service.findByName(name);
        else res = service.findAll();

        if(res == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return ResponseEntity.ok(batchGenerateDTO(res));
    }

    @GetMapping(value = "/raw")
    public ResponseEntity<List<Student>> getAllStudents(@RequestParam(required = false) Long departmentId) {
        List<Student> res = null;

        if (departmentId != null) res = service.findByDepartmentId(departmentId);
        else res = service.findAll();

        if(res == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return ResponseEntity.ok(res);
    }

    @GetMapping(value = "/{rollNo}")
    public ResponseEntity<StudentDTO> getByRollNo(@PathVariable("rollNo") Long rollNo) {
        Student s = service.findByRollNo(rollNo);
        StudentDTO dto = generateDTO(s);
        if(dto == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<StudentDTO> create(@RequestBody Student student) {
        String studentName = student.getName();
        Long rollNo = student.getRollNo();
        Long departmentId = student.getDepartmentId();

        Department department = departmentService.findDepartmentById(departmentId);
        if(department == null || rollNo == null || studentName == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        List<Long> courseIds = student.getCourseIds();
        courseIds = courseIds == null ? new ArrayList<>() : filterCourseIds(department.id(), courseIds);

        student = Student.builder().name(studentName).rollNo(rollNo).departmentId(department.id()).courseIds(courseIds).build();
        Student s = service.create(student);

        if(s == null) return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        return new ResponseEntity<>(generateDTO(s), HttpStatus.CREATED);
    }

    @PatchMapping(value = "/enroll")
    public ResponseEntity<StudentDTO> enrollToCourse(@RequestBody Student student) {
        Long rollNo = student.getRollNo();
        List<Long> courseIds = student.getCourseIds();

        if(rollNo == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Student s = service.findByRollNo(rollNo);
        if(s == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        courseIds = filterCourseIds(s.getDepartmentId(), courseIds);
        s.getCourseIds().addAll(courseIds);
        s = service.save(s);

        if(s == null) return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        return ResponseEntity.ok(generateDTO(s));
    }

    private List<Long> filterCourseIds(Long departmentId, List<Long> courseIds) {
        return courseService.getCoursesByIds(courseIds).stream().filter(c -> Objects.equals(c.departmentId(), departmentId)).map(Course::id).toList();
    }

    private List<StudentDTO> batchGenerateDTO(List<Student> students) {
        List<StudentDTO> res = new ArrayList<>();
        for(Student s : students) res.add(generateDTO(s));
        return res;
    }

    private StudentDTO generateDTO(Student s) {
        if(s == null) return null;
        Department d = departmentService.findDepartmentById(s.getDepartmentId());
        List<Course> courses = courseService.getCoursesByIds(s.getCourseIds()).stream().toList();
        return new StudentDTO(s.getId(), s.getName(),s.getRollNo(), d, courses);
    }
}
