package com.kalvi.tests.service;

import com.example.kalvi.entity.*;
import com.example.kalvi.entity.Module;
import com.example.kalvi.exception.CourseNotFoundException;
import com.example.kalvi.repository.CourseRepository;
import com.example.kalvi.repository.ModuleRepository;
import com.example.kalvi.repository.StudentRepository;
import com.example.kalvi.service.CourseService;
import org.checkerframework.checker.units.qual.C;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.testng.Assert.*;

public class CourseServiceTest {

    @InjectMocks
    private CourseService courseService;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private StudentRepository studentRepository;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test(description = "It test whether all the records are returned when calling getAllCourse method")
    public void testGetAllCourses() {
        List<Course> courses = new ArrayList<>();
        when(courseRepository.findAll()).thenReturn(courses);
        List<Course> result = courseService.getAllCourses();
        verify(courseRepository).findAll();
        assertEquals(result.size(), courses.size());
    }

    @Test(description = "It tests whether calling create course will save the course information")
    public void testCreateCourse() {
        Course course = new Course();
        when(courseRepository.save(course)).thenReturn(course);
        Course result = courseService.createCourse(course);
        verify(courseRepository).save(course);
        assertEquals(result, course);
    }

    @Test
    public void testAddModuleToCourse() {
        Course course = new Course();
        course.setId(1L);
        Module module = new Module();
        ArrayList<Module> modules = new ArrayList<>();
        modules.add(module);
        course.setModules(modules);

        when(courseRepository.findById(anyLong())).thenReturn(Optional.of(course));
        when(courseRepository.save(course)).thenReturn(course);


        Course result = courseService.addModuleToCourse(1L, module);

        assertTrue(result.getModules().contains(module));
        verify(courseRepository, times(1)).save(course);

        // test course without ratings in it : Scenario for first time
        Course course1 = new Course();
        course1.setId(1L);
        when(courseRepository.findById(anyLong())).thenReturn(Optional.of(course1));
        when(courseRepository.save(course1)).thenReturn(course1);

        Course result1 = courseService.addModuleToCourse(1L, module);
        assertTrue(result1.getModules().contains(module));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testAddModuleToCourseNotFound() {
        courseService.addModuleToCourse(1L, new Module());
    }

    @Test
    public void testAddRatingToCourse() {
        Course course = new Course();
        course.setId(1L);
        Rating rating = new Rating();
        ArrayList<Rating> ratings = new ArrayList<>();
        ratings.add(rating);
        course.setRatings(ratings);

        when(courseRepository.findById(anyLong())).thenReturn(Optional.of(course));
        when(courseRepository.save(course)).thenReturn(course);

        Course result = courseService.addRatingToCourse(1L, rating);

        assertTrue(result.getRatings().contains(rating));
        verify(courseRepository, times(1)).save(course);

        Course course1 = new Course();
        course1.setId(1L);
        when(courseRepository.findById(anyLong())).thenReturn(Optional.of(course1));
        when(courseRepository.save(course1)).thenReturn(course1);

        Course result1 = courseService.addRatingToCourse(1L, rating);
        assertTrue(result1.getRatings().contains(rating));

    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testAddRatingToCourseNotFound() {
        courseService.addRatingToCourse(1L, new Rating());
    }

    @Test
    public void testAddQuizToCourse() {
        Course course = new Course();
        Quiz quiz = new Quiz();

        when(courseRepository.findById(anyLong())).thenReturn(Optional.of(course));

        Quiz result = courseService.addQuizToCourse(1L, quiz);

        assertNotNull(result.getCourse());
        assertTrue(course.getQuizzes().contains(result));
        verify(courseRepository, times(1)).save(course);
    }

    @Test(expectedExceptions = CourseNotFoundException.class)
    public void testAddQuizToCourseNotFound() {
        courseService.addQuizToCourse(1L, new Quiz());
    }

    @Test
    public void testAddAssignmentToCourse() {
        Course course = new Course();
        Assignment assignment = new Assignment();

        when(courseRepository.findById(anyLong())).thenReturn(Optional.of(course));

        Assignment result = courseService.addAssignmentToCourse(1L, assignment);

        assertNotNull(result.getCourse());
        assertTrue(course.getAssignments().contains(result));
        verify(courseRepository, times(1)).save(course);
    }

    @Test(expectedExceptions = CourseNotFoundException.class)
    public void testAddAssignmentToCourseNotFound() {
        courseService.addAssignmentToCourse(1L, new Assignment());
    }

    @Test
    public void testIsStudentEnrolledForCourse() {
        Student student = new Student();
        student.setId(1L);
        Course course = new Course();
        course.setId(2L);
        student.getCourses().add(course);

        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

        boolean result = courseService.isStudentEnrolledForCourse(1L, 2L);

        assertTrue(result);
    }

    @Test
    public void testIsStudentNotEnrolledForCourse() {
        Student student = new Student();
        student.setId(1L);
        Course course = new Course();
        course.setId(2L);

        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

        boolean result = courseService.isStudentEnrolledForCourse(1L, 2L);

        assertFalse(result);
    }

    @Test
    public void testIsStudentEnrolledForNonExistingCourse() {
        when(studentRepository.findById(1L)).thenReturn(Optional.empty());

        boolean result = courseService.isStudentEnrolledForCourse(1L, 2L);

        assertFalse(result);
    }
}
