package com.example.kalvi.controller;

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
    public ResponseEntity<List<Course>> getAllCourses() {
        List<Course> courses = courseService.getAllCourses();
        return new ResponseEntity<>(courses, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<Course> createCourse(@RequestBody Course course) {
        Course createdCourse = courseService.createCourse(course);
        return new ResponseEntity<>(createdCourse, HttpStatus.CREATED);
    }

    @PostMapping("/{courseId}/modules")
    public ResponseEntity<Course> addModuleToCourse(@PathVariable Long courseId, @RequestBody Module module) {
        Course addedModule = courseService.addModuleToCourse(courseId, module);
        return new ResponseEntity<>(addedModule, HttpStatus.CREATED);
    }

    @PostMapping("/{courseId}/quizzes")
    public ResponseEntity<Quiz> addQuizToCourse(@PathVariable Long courseId, @RequestBody Quiz quiz) {
        Quiz addedQuiz = courseService.addQuizToCourse(courseId, quiz);
        return new ResponseEntity<>(addedQuiz, HttpStatus.CREATED);
    }

    @PostMapping("/{courseId}/assignments")
    public ResponseEntity<Assignment> addAssignmentToCourse(@PathVariable Long courseId, @RequestBody Assignment assignment) {
        Assignment addedAssignment = courseService.addAssignmentToCourse(courseId, assignment);
        return new ResponseEntity<>(addedAssignment, HttpStatus.CREATED);
    }
}
