package com.irex.StudentService.service;

import com.irex.StudentService.record.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class CourseService {
    @Autowired
    private RestTemplate restTemplate;

    public List<Course> getCoursesByIds(List<Long> ids) {
        try {
            String url = "http://localhost:8002/api/courses/by-ids";
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<List<Long>> entity = new HttpEntity<>(ids, headers);

            ParameterizedTypeReference<List<Course>> responseType = new ParameterizedTypeReference<>() {};

            return restTemplate.exchange(url, HttpMethod.POST, entity, responseType).getBody();
        } catch (RestClientException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public List<Course> getCoursesByNames(List<String> names) {
        try {
            String url = "http://localhost:8002/api/courses/by-names";
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<List<String>> entity = new HttpEntity<>(names, headers);

            ParameterizedTypeReference<List<Course>> responseType = new ParameterizedTypeReference<>() {};

            return restTemplate.exchange(url, HttpMethod.POST, entity, responseType).getBody();
        } catch (RestClientException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    private List<Course> getCourses() {
        try {
            String url = "http://localhost:8002/api/courses";
            ParameterizedTypeReference<List<Course>> responseType = new ParameterizedTypeReference<>() {};
            return restTemplate.exchange(url, HttpMethod.GET, null, responseType).getBody();
        } catch (RestClientException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
