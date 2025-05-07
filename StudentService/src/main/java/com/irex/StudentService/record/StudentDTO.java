package com.irex.StudentService.record;

import java.util.List;

public record StudentDTO(Long id, String name, Long rollNo, Department department, List<Course> courses) {
}
