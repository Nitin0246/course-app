package com.courseapp.course_search.controller;

import com.courseapp.course_search.document.CourseDocument;
import com.courseapp.course_search.service.CourseSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseSearchController {

    @Autowired
    private CourseSearchService courseSearchService;

    @GetMapping("/search")
    public List<CourseDocument> searchCourses(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Integer minAge,
            @RequestParam(required = false) Integer maxAge
    ) {
        return courseSearchService.searchCourses(keyword, category, minAge, maxAge);
    }
}

