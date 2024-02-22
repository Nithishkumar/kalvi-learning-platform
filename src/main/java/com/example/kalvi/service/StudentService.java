package com.example.kalvi.service;

import com.example.kalvi.entity.*;
import com.example.kalvi.exception.StudentAlreadyEnrolledException;
import com.example.kalvi.exception.StudentProgressNotFoundException;
import com.example.kalvi.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private StudentProgressRepository studentProgressRepository;

    @Autowired
    private QuizProgressRepository quizProgressRepository;

    @Autowired
    private AssignmentProgressRepository assignmentProgressRepository;

    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    @Transactional
    public void enrollForCourse(Long studentId, Long courseId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Student not found"));
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Course not found"));

        // Add the course to the student's list of courses

        for(Course enrolledCourses :student.getCourses()){
            if(enrolledCourses.getId().equals(studentId)){
                throw new StudentAlreadyEnrolledException("Student already enrolled in following course");
            }
        }
        student.getCourses().add(course);

        // Create a new StudentProgress for this course
        StudentProgress studentProgress = new StudentProgress();
        studentProgress.setStudent(student);
        studentProgress.setCourse(course);

        // Initialize QuizProgress for each quiz in the course
        for (Quiz quiz : course.getQuizzes()) {
            QuizProgress quizProgress = new QuizProgress();
            quizProgress.setQuiz(quiz);
            quizProgress.setStudentProgress(studentProgress);
            quizProgress.setScore(-1); // Indicative of not attempted
            studentProgress.getQuizProgresses().add(quizProgress);
        }

        // Initialize AssignmentProgress for each assignment in the course
        for (Assignment assignment : course.getAssignments()) {
            AssignmentProgress assignmentProgress = new AssignmentProgress();
            assignmentProgress.setAssignment(assignment);
            assignmentProgress.setStudentProgress(studentProgress);
            assignmentProgress.setSubmitted(false); // Indicative of not submitted
            studentProgress.getAssignmentProgresses().add(assignmentProgress);
        }
        student.getProgress().add(studentProgress);
        // Save the student to update the association in the database
        studentRepository.save(student);
    }
}
