package com.irex.DepartmentService.record;

import java.util.List;

public record DepartmentDTO (Long id, String name, List<Course> courses, List<Student> students){
}
