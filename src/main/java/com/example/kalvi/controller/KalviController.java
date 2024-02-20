package com.example.kalvi.controller;

import com.example.kalvi.entity.Course;
import com.example.kalvi.entity.Module;
import com.example.kalvi.entity.Student;
import com.example.kalvi.service.CourseService;
import com.example.kalvi.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class KalviController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private StudentService studentService;

    @GetMapping("/courses")
    public ResponseEntity<List<Course>> getAllCourses() {
        List<Course> courses = courseService.getAllCourses();
        return new ResponseEntity<>(courses, HttpStatus.OK);
    }

    @PostMapping("/courses")
    public ResponseEntity<Course> createCourse(@RequestBody Course course) {
        Course createdCourse = courseService.createCourse(course);
        return new ResponseEntity<>(createdCourse, HttpStatus.CREATED);
    }

    @PostMapping("/{courseId}/modules")
    public ResponseEntity<Module> addModuleToCourse(@PathVariable Long courseId, @RequestBody Module module) {
        Module addedModule = courseService.addModuleToCourse(courseId, module);
        return new ResponseEntity<>(addedModule, HttpStatus.CREATED);
    }

    @PostMapping("/student")
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        // Encrypt the password before saving
        String encryptedPassword = passwordEncoder.encode(student.getPassword());
        student.setPassword(encryptedPassword);

        Student createdStudent = studentService.createStudent(student);
        return new ResponseEntity<>(createdStudent, HttpStatus.CREATED);
    }

    @PostMapping("/enrollments")
    public ResponseEntity<Void> enrollForCourse(@RequestParam Long studentId, @RequestParam Long courseId) {
        studentService.enrollForCourse(studentId, courseId);
        return ResponseEntity.ok().build();
    }
}
