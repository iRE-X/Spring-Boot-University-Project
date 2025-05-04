package com.irex.StudentService.record;

import java.util.List;

public record StudentDTO(String name, Long rollNo, String departmentName, List<String> courses) {
}
