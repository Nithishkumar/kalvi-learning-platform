package com.example.kalvi.controller;

import com.example.kalvi.entity.Course;
import com.example.kalvi.entity.Module;
import com.example.kalvi.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class KalviController {

    @Autowired
    private CourseService courseService;

    @GetMapping
    public ResponseEntity<List<Course>> getAllCourses() {
        List<Course> courses = courseService.getAllCourses();
        return new ResponseEntity<>(courses, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Course> createCourse(@RequestBody Course course) {
        Course createdCourse = courseService.createCourse(course);
        return new ResponseEntity<>(createdCourse, HttpStatus.CREATED);
    }

    @PostMapping("/{courseId}/modules")
    public ResponseEntity<Module> addModuleToCourse(@PathVariable Long courseId, @RequestBody Module module) {
        Module addedModule = courseService.addModuleToCourse(courseId, module);
        return new ResponseEntity<>(addedModule, HttpStatus.CREATED);
    }
}
