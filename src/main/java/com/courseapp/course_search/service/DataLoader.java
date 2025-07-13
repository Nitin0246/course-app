package com.courseapp.course_search.service;

import com.courseapp.course_search.document.CourseDocument;
import com.courseapp.course_search.repository.CourseRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DataLoader {

    private final CourseRepository courseRepository;

    @PostConstruct
    public void loadData() {
        try {
            InputStream inputStream = getClass().getResourceAsStream("/sample-courses.json");
            ObjectMapper objectMapper = new ObjectMapper();
            List<CourseDocument> courses = objectMapper.<List<CourseDocument>>readValue(inputStream, new TypeReference<>() {
            });
            courseRepository.saveAll(courses);
            System.out.println(" Courses indexed successfully into Elasticsearch.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
