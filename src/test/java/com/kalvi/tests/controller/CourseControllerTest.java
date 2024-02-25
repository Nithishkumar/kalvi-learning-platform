package com.kalvi.tests.controller;

import com.example.kalvi.controller.CourseController;
import com.example.kalvi.dto.CourseDTO;
import com.example.kalvi.entity.*;
import com.example.kalvi.entity.Module;
import com.example.kalvi.service.CourseService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

public class CourseControllerTest {

    @Mock
    private CourseService courseService;

    @InjectMocks
    private CourseController courseController;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllCourses() {
        List<CourseDTO> courses = new ArrayList<>();
        when(courseService.getAllCourses()).thenReturn(courses);

        ResponseEntity<List<CourseDTO>> response = courseController.getAllCourses();

        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(response.getBody(), courses);
    }

    @Test
    public void testCreateCourse() {
        Course course = new Course();
        Course courseStub = new Course();
        courseStub.setId(1L);

        when(courseService.createCourse(any(Course.class))).thenReturn(courseStub);

        ResponseEntity<String> response = courseController.createCourse(course);

        assertEquals(response.getStatusCode(), HttpStatus.CREATED);
        assertEquals(response.getBody(), "Created course with with id " + 1L);
    }

    @Test
    public void testAddModuleToCourse() {
        Long courseId = 1L;
        Module module = new Module();
        // Set module properties
        Course courseStub = new Course();
        courseStub.setId(1L);

        when(courseService.addModuleToCourse(eq(courseId), any(Module.class))).thenReturn(courseStub);

        ResponseEntity<String> response = courseController.addModuleToCourse(courseId, module);

        assertEquals(response.getStatusCode(), HttpStatus.CREATED);
        assertEquals(response.getBody(), "Created module with id " + courseStub.getId().toString());
    }

    @Test
    public void testAddRatingToCourse() {
        Long courseId = 1L;
        Rating rating = new Rating();
        Course courseStub = new Course();
        courseStub.setId(1L);

        when(courseService.addRatingToCourse(eq(courseId), any(Rating.class))).thenReturn(courseStub);

        ResponseEntity<String> response = courseController.addRatingToCourse(courseId, rating);

        assertEquals(response.getStatusCode(), HttpStatus.CREATED);
        assertEquals(response.getBody(), "Created rating with id " + courseStub.getId().toString());
    }

    @Test
    public void testAddQuizToCourse() {
        Long courseId = 1L;
        Quiz quiz = new Quiz();
        Quiz quizStub = new Quiz();
        quizStub.setId(1L);

        when(courseService.addQuizToCourse(eq(courseId), any(Quiz.class))).thenReturn(quizStub);

        ResponseEntity<String> response = courseController.addQuizToCourse(courseId, quiz);

        assertEquals(response.getStatusCode(), HttpStatus.CREATED);
        assertEquals(response.getBody(), "Created quiz with id " + quizStub.getId().toString());
    }

    @Test
    public void testAddAssignmentToCourse() {
        Long courseId = 1L;
        Assignment assignment = new Assignment();
        // Set assignment properties
        Assignment assignmentStub = new Assignment();
        assignmentStub.setId(1L);
        when(courseService.addAssignmentToCourse(eq(courseId), any(Assignment.class))).thenReturn(assignmentStub);

        ResponseEntity<String> response = courseController.addAssignmentToCourse(courseId, assignment);

        assertEquals(response.getStatusCode(), HttpStatus.CREATED);
        assertEquals(response.getBody(), "Created assignment with id " + assignmentStub.getId().toString());
    }
}
