package com.irex.DepartmentService.service;

import com.irex.DepartmentService.record.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class CourseService {
    @Autowired
    private RestTemplate restTemplate;

    public List<Course> getCoursesByDepartment(Long deptId) {
        try {
            String url = "http://localhost:8002/api/courses/raw?departmentId={departmentId}";
            ParameterizedTypeReference<List<Course>> responseType = new ParameterizedTypeReference<>() {};
            return restTemplate.exchange(url, HttpMethod.GET, null, responseType, deptId).getBody();
        } catch (RestClientException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
