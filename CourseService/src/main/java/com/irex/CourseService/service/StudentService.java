package com.irex.CourseService.service;

import com.irex.CourseService.record.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class StudentService {
    @Autowired
    private RestTemplate restTemplate;

    public List<Student> getStudentsByCourseId(Long courseId) {
        try {
            String url = "http://localhost:8003/api/students/raw?courseId={courseId}";
            ParameterizedTypeReference<List<Student>> responseType = new ParameterizedTypeReference<>() {};
            return restTemplate.exchange(url, HttpMethod.GET, null, responseType, courseId).getBody();

        } catch (RestClientException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
