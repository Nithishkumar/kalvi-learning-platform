package com.kalvi.tests.service;

import com.example.kalvi.entity.Course;
import com.example.kalvi.repository.CourseRepository;
import com.example.kalvi.service.CourseService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.Mockito.*;
import static org.testng.Assert.assertEquals;

public class CourseServiceTest {

    @InjectMocks
    private CourseService courseService;

    @Mock
    private CourseRepository courseRepository;

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
}
