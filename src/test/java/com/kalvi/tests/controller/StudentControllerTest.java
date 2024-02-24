package com.kalvi.tests.controller;

import com.example.kalvi.controller.StudentController;
import com.example.kalvi.dto.StudentProgressDTO;
import com.example.kalvi.entity.*;
import com.example.kalvi.exception.*;
import com.example.kalvi.service.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

public class StudentControllerTest {

    @Mock
    private StudentService studentService;

    @Mock
    private CourseService courseService;

    @Mock
    private QuizService quizService;

    @Mock
    private StudentProgressService studentProgressService;

    @Mock
    private AssignmentService assignmentService;

    @InjectMocks
    private StudentController studentController;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateStudent_Success() {
        Student student = new Student();
        student.setUsername("testuser");
        student.setPassword("password");

        Student studentStub = new Student();
        studentStub.setId(1L);

        when(passwordEncoder.encode(anyString())).thenReturn("encryptedPassword");
        when(studentService.createStudent(any(Student.class))).thenReturn(studentStub);

        ResponseEntity<String> response = studentController.createStudent(student);

        assertEquals(response.getStatusCode(), HttpStatus.CREATED);
        assertEquals(response.getBody(), "Created quiz with id " + 1L);
    }

    @Test
    public void testEnrollForCourse_Success() {
        Long studentId = 1L;
        Long courseId = 1L;

        ResponseEntity<Void> expectedResponse = ResponseEntity.ok().build();
        doNothing().when(studentService).enrollForCourse(eq(studentId), eq(courseId));

        ResponseEntity<Void> response = studentController.enrollForCourse(studentId, courseId);

        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test()
    public void testSubmitQuiz_Success() {
        Long studentId = 1L;
        Long courseId = 1L;
        Long quizId = 1L;
        List<Answer> answers = new ArrayList<>(); // Add some answers

        when(courseService.isStudentEnrolledForCourse(eq(studentId), eq(courseId))).thenReturn(true);

        ResponseEntity<String> response = studentController.submitQuiz(studentId, courseId, quizId, answers);

        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(response.getBody(), "Quiz submitted successfully");
    }
    @Test()
    public void testSubmitQuiz_Student_Not_Enrolled() {
        Long studentId = 1L;
        Long courseId = 1L;
        Long quizId = 1L;
        List<Answer> answers = new ArrayList<>(); // Add some answers

        when(courseService.isStudentEnrolledForCourse(eq(studentId), eq(courseId))).thenReturn(false);

        ResponseEntity<String> response = studentController.submitQuiz(studentId, courseId, quizId, answers);
        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);

    }

    @Test()
    public void testSubmitQuiz_Internal_Server_Error() {
        Long studentId = 1L;
        Long courseId = 1L;
        Long quizId = 1L;
        List<Answer> answers = new ArrayList<>(); // Add some answers

        when(courseService.isStudentEnrolledForCourse(eq(studentId), eq(courseId))).thenThrow(NullPointerException.class);

        ResponseEntity<String> response = studentController.submitQuiz(studentId, courseId, quizId, answers);
        assertEquals(response.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @Test
    public void testSubmitQuiz_StudentNotEnrolled() {
        Long studentId = 1L;
        Long courseId = 2L;
        Long quizId = 3L;
        List<Answer> answers = new ArrayList<>(); // Add some answers

        when(courseService.isStudentEnrolledForCourse(eq(studentId), eq(courseId)))
                .thenThrow(new StudentNotEnrolledException("You are not enrolled for this course"));

        ResponseEntity<String> response = studentController.submitQuiz(studentId, courseId, quizId, answers);

        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    public void testGetStudentProgress_Success() {
        Long studentId = 1L;
        List<StudentProgress> studentProgressList = new ArrayList<>(); // Add some student progress

        when(studentProgressService.getAllStudentProgress(eq(studentId))).thenReturn(studentProgressList);

        ResponseEntity<List<StudentProgressDTO>> response = studentController.getStudentProgress(studentId);

        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertNotNull(response.getBody());
    }

    @Test
    public void testSubmitAssignment_Success() {
        Long studentId = 1L;
        Long courseId = 2L;
        Long assignmentId = 3L;
        String assignmentAnswer = "Some answer";

        ResponseEntity<String> response = studentController.submitAssignment(studentId, courseId, assignmentId, assignmentAnswer);

        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(response.getBody(), "Assignment submitted successfully.");
    }

    @Test
    public void testSubmitAssignment_Internal_Server_Error() {
        Long studentId = 1L;
        Long courseId = 1L;
        Long assignmentId = 1L;
        String assignmentAnswer = "sample assignment";

        doThrow(NullPointerException.class).when(assignmentService).submitAssignment(eq(studentId), eq(courseId), eq(assignmentId), anyString());

        ResponseEntity<String> response = studentController.submitAssignment(studentId, courseId, assignmentId, assignmentAnswer);

        assertEquals(response.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
