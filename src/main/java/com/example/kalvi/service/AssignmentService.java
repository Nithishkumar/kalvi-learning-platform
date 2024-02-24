package com.example.kalvi.service;

import com.example.kalvi.entity.*;
import com.example.kalvi.exception.*;
import com.example.kalvi.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AssignmentService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private AssignmentProgressRepository assignmentProgressRepository;

    @Autowired
    private StudentProgressRepository studentProgressRepository;

    @Autowired
    private AssignmentRepository assignmentRepository;
    @Transactional
    public void submitAssignment(Long studentId, Long courseId, Long assignmentId, String assignmentAnswer) {
        // Fetch the student from repositoy
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new StudentNotFoundException("Student not found with id: " + studentId));

        // Fetch the course from course repo
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseNotFoundException("Course not found with id: " + courseId));

        // Fetch the assignment from assignment repo
        Assignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> {
                    return new AssignmentNotFoundException("Assignment not found with id: " + assignmentId);
                });

        // Check if the student is enrolled in the course
        if (!student.getCourses().contains(course)){
            throw new StudentNotEnrolledToCourseException("Student is not enrolled in the course.");
        }

        if(assignmentAnswer == null || assignmentAnswer.equals("")){
            throw new AssignmentNotValidException("Assignment is not valid");
        }

        StudentProgress studentProgress = student.getProgress().stream()
                .filter(progress -> progress.getCourse().equals(course))
                .findFirst()
                .orElseThrow(() -> new StudentProgressNotFoundException("Student progress not found for course and quiz"));



        for(AssignmentProgress assignmentProgressAvailable : studentProgress.getAssignmentProgresses()){
            if(assignmentProgressAvailable.getId().equals(assignmentId)){
                assignmentProgressAvailable.setSubmitted(true);
                studentProgressRepository.save(studentProgress);
                return;
            }
        }
        AssignmentProgress assignmentProgress = new AssignmentProgress();
        assignmentProgress.setStudentProgress(studentProgress);
        assignmentProgress.setAssignment(assignment);
        assignmentProgress.setSubmitted(true);
        studentProgress.getAssignmentProgresses().add(assignmentProgress);
        studentProgressRepository.save(studentProgress);
    }
}
