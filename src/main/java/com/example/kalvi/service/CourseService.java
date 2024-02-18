package com.example.kalvi.service;

import com.example.kalvi.entity.Course;
import com.example.kalvi.entity.Module;
import com.example.kalvi.repository.CourseRepository;
import com.example.kalvi.repository.ModuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private ModuleRepository moduleRepository;

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Course createCourse(Course course) {
        return courseRepository.save(course);
    }

    public Module addModuleToCourse(Long courseId, Module module) {
        Optional<Course> optionalCourse = courseRepository.findById(courseId);
        if (optionalCourse.isPresent()) {
            Course course = optionalCourse.get();
            module.setCourse(course);
            return moduleRepository.save(module);
        } else {
            throw new IllegalArgumentException("Course not found with id: " + courseId);
        }
    }
}