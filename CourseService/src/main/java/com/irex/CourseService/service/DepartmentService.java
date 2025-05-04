package com.irex.CourseService.service;

import com.irex.CourseService.record.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

@Service
public class DepartmentService {
    @Autowired
    private RestTemplate restTemplate;

    public Department findDepartmentByName(String departmentName) {
        List<Department> departments = getAllDepartments();
        if(departments == null) return null;

        for(Department d : departments) {
            if(d.name().equalsIgnoreCase(departmentName)) return d;
        }

        return null;
    }

    public Department findDepartmentById(Long id) {
            List<Department> departments = getAllDepartments();
            if(departments == null) return null;

            for(Department d : departments) {
                if(Objects.equals(d.id(), id)) return d;
            }

            return null;
    }

    private List<Department> getAllDepartments() {
        try {
            String url = "http://localhost:8001/api/departments/raw";
            ParameterizedTypeReference<List<Department>> responseType = new ParameterizedTypeReference<>() {};
            return restTemplate.exchange(url, HttpMethod.GET, null, responseType).getBody();

        } catch (RestClientException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

}
