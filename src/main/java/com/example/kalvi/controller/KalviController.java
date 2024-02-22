package com.example.kalvi.controller;

import com.example.kalvi.dto.StudentProgressDTO;
import com.example.kalvi.entity.*;
import com.example.kalvi.entity.Module;
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
@RequestMapping("/api")
public class KalviController {

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
    public ResponseEntity<Course> addModuleToCourse(@PathVariable Long courseId, @RequestBody Module module) {
        Course addedModule = courseService.addModuleToCourse(courseId, module);
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


    @PostMapping("/students/{studentId}/courses/{courseId}/quizzes/{quizId}/submit")
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
    @GetMapping("/students/{studentId}/progress")
    public ResponseEntity<List<StudentProgressDTO>> getStudentProgress(@PathVariable Long studentId) {
        List<StudentProgress> studentProgressList = studentProgressService.getAllStudentProgress(studentId);

        List<StudentProgressDTO> studentProgressDTOList = studentProgressList.stream()
                .map(StudentProgressService::convertToDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(studentProgressDTOList);
    }

    @PostMapping("/students/{studentId}/courses/{courseId}/assignments/{assignmentId}/submit")
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
