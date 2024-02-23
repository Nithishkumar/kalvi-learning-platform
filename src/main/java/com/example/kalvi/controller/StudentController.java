package com.example.kalvi.controller;

import com.example.kalvi.dto.StudentProgressDTO;
import com.example.kalvi.entity.*;
import com.example.kalvi.exception.*;
import com.example.kalvi.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/student")
public class StudentController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private StudentService studentService;
    @Autowired
    private QuizService quizService;

    @Autowired
    private StudentProgressService studentProgressService;

    @Autowired
    private AssignmentService assignmentService;


    @PostMapping()
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

    @PostMapping("/{studentId}/courses/{courseId}/quizzes/{quizId}/submit")
    public ResponseEntity<String> submitQuiz(@PathVariable Long studentId,
                                             @PathVariable Long courseId,
                                             @PathVariable Long quizId,
                                             @RequestBody List<Answer> answers) {

        try {
            boolean isEnrolled = courseService.isStudentEnrolledForCourse(studentId, courseId);

            if (!isEnrolled) {
                throw new StudentNotEnrolledException("You are not enrolled for this course");
            }
            quizService.submitQuiz(studentId, courseId, quizId, answers);
            return ResponseEntity.ok("Quiz submitted successfully");
        } catch (CourseNotFoundException | StudentNotFoundException |
                 QuizNotFoundException | InvalidQuizSubmissionException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while submitting the quiz");
        }
    }

    // Endpoint to get student progress by student ID
    @GetMapping("/{studentId}/progress")
    public ResponseEntity<List<StudentProgressDTO>> getStudentProgress(@PathVariable Long studentId) {
        List<StudentProgress> studentProgressList = studentProgressService.getAllStudentProgress(studentId);

        List<StudentProgressDTO> studentProgressDTOList = studentProgressList.stream()
                .map(StudentProgressService::convertToDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(studentProgressDTOList);
    }

    @PostMapping("/{studentId}/courses/{courseId}/assignments/{assignmentId}/submit")
    public ResponseEntity<String> submitAssignment(@PathVariable Long studentId,
                                                   @PathVariable Long courseId,
                                                   @PathVariable Long assignmentId,
                                                   @RequestBody String assignmentAnswer) {
        try {
            assignmentService.submitAssignment(studentId, courseId, assignmentId, assignmentAnswer);
            return ResponseEntity.ok("Assignment submitted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to submit assignment: " + e.getMessage());
        }
    }
}
