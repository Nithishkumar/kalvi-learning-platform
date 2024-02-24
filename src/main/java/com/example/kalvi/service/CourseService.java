package com.example.kalvi.service;

import com.example.kalvi.entity.*;
import com.example.kalvi.entity.Module;
import com.example.kalvi.exception.CourseNotFoundException;
import com.example.kalvi.repository.CourseRepository;
import com.example.kalvi.repository.ModuleRepository;
import com.example.kalvi.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private ModuleRepository moduleRepository;

    @Autowired
    private StudentRepository studentRepository;
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Course createCourse(Course course) {
        return courseRepository.save(course);
    }

    public Course addModuleToCourse(Long courseId, Module module) {
        Optional<Course> optionalCourse = courseRepository.findById(courseId);
        if (optionalCourse.isPresent()) {
            Course course = optionalCourse.get();
            if(course.getModules() == null ) {
                course.setModules(new ArrayList<>());
            }
            course.getModules().add(module);
            return courseRepository.save(course);
        } else {
            throw new IllegalArgumentException("Course not found with id: " + courseId);
        }
    }

    public Course addRatingToCourse(Long courseId, Rating rating) {
        Optional<Course> optionalCourse = courseRepository.findById(courseId);
        if (optionalCourse.isPresent()) {
            Course course = optionalCourse.get();
            if(course.getRatings() == null){
                course.setRatings(new ArrayList<>());
            }
            course.getRatings().add(rating);
            return courseRepository.save(course);
        } else {
            throw new IllegalArgumentException("Course not found with id: " + courseId);
        }
    }

    public Quiz addQuizToCourse(Long courseId, Quiz quiz) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseNotFoundException("Course not found with id: " + courseId));
        quiz.setCourse(course);
        course.getQuizzes().add(quiz);
        courseRepository.save(course);
        return quiz;
    }

    public Assignment addAssignmentToCourse(Long courseId, Assignment assignment) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseNotFoundException("Course not found with id: " + courseId));
        assignment.setCourse(course);
        course.getAssignments().add(assignment);
        courseRepository.save(course);
        return assignment;
    }

    public boolean isStudentEnrolledForCourse(Long studentId, Long courseId) {
        Optional<Student> student = studentRepository.findById(studentId);
        if(student.isPresent()){
            for(Course course :student.get().getCourses()){
                if (course.getId().equals(courseId)) {
                    return true;
                }
            }
        }
        return false;
    }
}