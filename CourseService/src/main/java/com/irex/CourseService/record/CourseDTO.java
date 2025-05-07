package com.irex.CourseService.record;

import java.util.List;

public record CourseDTO(Long id, String name, Department department, List<Student> students) {
}
