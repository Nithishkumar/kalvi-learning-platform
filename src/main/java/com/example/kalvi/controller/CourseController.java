package com.example.kalvi.controller;

import com.example.kalvi.dto.CourseDTO;
import com.example.kalvi.entity.*;
import com.example.kalvi.entity.Module;
import com.example.kalvi.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;


    @GetMapping()
    public ResponseEntity<List<CourseDTO>> getAllCourses() {
        List<CourseDTO> courses = courseService.getAllCourses();
        return new ResponseEntity<>(courses, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<String> createCourse(@RequestBody Course course) {
        Course createdCourse = courseService.createCourse(course);
        return ResponseEntity.status(HttpStatus.CREATED).body("Created course with with id " + createdCourse.getId().toString());
    }

    @PostMapping("/{courseId}/modules")
    public ResponseEntity<String> addModuleToCourse(@PathVariable Long courseId, @RequestBody Module module) {
        Course addedModule = courseService.addModuleToCourse(courseId, module);
        return ResponseEntity.status(HttpStatus.CREATED).body("Created module with id " + addedModule.getId().toString());
    }

    @PostMapping("/{courseId}/ratings")
    public ResponseEntity<String> addRatingToCourse(@PathVariable Long courseId, @RequestBody Rating rating) {
        Course addedModule = courseService.addRatingToCourse(courseId, rating);
        return ResponseEntity.status(HttpStatus.CREATED).body("Created rating with id " + addedModule.getId().toString());
    }

    @PostMapping("/{courseId}/quizzes")
    public ResponseEntity<String> addQuizToCourse(@PathVariable Long courseId, @RequestBody Quiz quiz) {
        Quiz addedQuiz = courseService.addQuizToCourse(courseId, quiz);
        return ResponseEntity.status(HttpStatus.CREATED).body("Created quiz with id " + addedQuiz.getId().toString());
    }

    @PostMapping("/{courseId}/assignments")
    public ResponseEntity<String> addAssignmentToCourse(@PathVariable Long courseId, @RequestBody Assignment assignment) {
        Assignment addedAssignment = courseService.addAssignmentToCourse(courseId, assignment);
        return ResponseEntity.status(HttpStatus.CREATED).body("Created assignment with id " + addedAssignment.getId().toString());
    }
}
