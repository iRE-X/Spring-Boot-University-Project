package com.irex.DepartmentService.record;

import java.util.List;

public record DepartmentDTO (String name, List<String> courses, List<Student> students){
}
