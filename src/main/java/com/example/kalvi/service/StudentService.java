package com.example.kalvi.service;

import com.example.kalvi.entity.Course;
import com.example.kalvi.entity.Student;
import com.example.kalvi.repository.CourseRepository;
import com.example.kalvi.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    public void enrollForCourse(Long studentId, Long courseId) {
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new IllegalArgumentException("Student not found"));
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new IllegalArgumentException("Course not found"));
        student.getCourses().add(course);
        studentRepository.save(student);
    }
}
