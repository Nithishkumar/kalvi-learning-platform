package com.kalvi.tests.service;

import com.example.kalvi.entity.*;
import com.example.kalvi.exception.*;
import com.example.kalvi.repository.*;
import com.example.kalvi.service.AssignmentService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.*;
import static org.testng.Assert.*;

public class AssignmentServiceTest {

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private AssignmentProgressRepository assignmentProgressRepository;

    @Mock
    private StudentProgressRepository studentProgressRepository;

    @Mock
    private AssignmentRepository assignmentRepository;

    @InjectMocks
    private AssignmentService assignmentService;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSubmitAssignment_Success() {
        // Mock data for test
        Long studentId = 1L;
        Long courseId = 1L;
        Long assignmentId = 1L;
        String assignmentAnswer = "Assignment answer";

        Course course = new Course();
        course.setId(courseId);

        Student student = new Student();
        student.setId(studentId);
        Set<Course> courses = new HashSet<>();
        courses.add(course);
        student.setCourses(courses);

        Assignment assignment = new Assignment();
        assignment.setId(assignmentId);


        StudentProgress studentProgress = new StudentProgress();
        studentProgress.setCourse(course);
        Set<StudentProgress> studentProgresses = new HashSet<>();
        studentProgresses.add(studentProgress);
        AssignmentProgress assignmentProgress = new AssignmentProgress();
        assignmentProgress.setId(1L);
        assignmentProgress.setSubmitted(true);
        assignmentProgress.setStudentProgress(studentProgress);
        Set<AssignmentProgress>assignmentProgresses = new HashSet<>();
        assignmentProgresses.add(assignmentProgress);
        studentProgress.setAssignmentProgresses(assignmentProgresses);
        student.setProgress(studentProgresses);

        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));
        when(assignmentRepository.findById(assignmentId)).thenReturn(Optional.of(assignment));
        when(studentProgressRepository.save(any())).thenReturn(studentProgress);

        // Test
        assignmentService.submitAssignment(studentId, courseId, assignmentId, assignmentAnswer);

        for(AssignmentProgress assignmentProgress1 : studentProgress.getAssignmentProgresses()){
            assertTrue(assignmentProgress1.isSubmitted());

        }
        verify(studentProgressRepository, times(1)).save(studentProgress);
    }

    @Test
    public void testSubmitAssignment_SuccessWithoutAssignmentProgress() {
        // Mock the data for testng
        Long studentId = 1L;
        Long courseId = 1L;
        Long assignmentId = 1L;
        String assignmentAnswer = "Assignment answer";

        Course course = new Course();
        course.setId(courseId);

        Student student = new Student();
        student.setId(studentId);
        Set<Course> courses = new HashSet<>();
        courses.add(course);
        student.setCourses(courses);

        Assignment assignment = new Assignment();
        assignment.setId(assignmentId);


        StudentProgress studentProgress = new StudentProgress();
        studentProgress.setCourse(course);
        Set<StudentProgress> studentProgresses = new HashSet<>();
        studentProgresses.add(studentProgress);
        student.setProgress(studentProgresses);

        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));
        when(assignmentRepository.findById(assignmentId)).thenReturn(Optional.of(assignment));
        when(studentProgressRepository.save(any())).thenReturn(studentProgress);

        // Test
        assignmentService.submitAssignment(studentId, courseId, assignmentId, assignmentAnswer);

        for(AssignmentProgress assignmentProgress1 : studentProgress.getAssignmentProgresses()){
            assertTrue(assignmentProgress1.isSubmitted());

        }
        verify(studentProgressRepository, times(1)).save(studentProgress);
    }


    @Test(expectedExceptions = {StudentNotFoundException.class})
    public void testSubmitAssignment_StudentNotFound() {
        Long studentId = 1L;
        Long courseId = 2L;
        Long assignmentId = 3L;
        String assignmentAnswer = "Assignment answer";

        when(studentRepository.findById(studentId)).thenReturn(Optional.empty());

        assignmentService.submitAssignment(studentId, courseId, assignmentId, assignmentAnswer);
    }

    @Test(expectedExceptions = {CourseNotFoundException.class})
    public void testSubmitAssignment_CourseNotFound() {
        Long studentId = 1L;
        Long courseId = 2L;
        Long assignmentId = 3L;
        String assignmentAnswer = "Assignment answer";

        Student student = new Student();
        student.setId(studentId);

        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));
        when(courseRepository.findById(courseId)).thenReturn(Optional.empty());

        assignmentService.submitAssignment(studentId, courseId, assignmentId, assignmentAnswer);
    }

    @Test(expectedExceptions = {AssignmentNotFoundException.class})
    public void testSubmitAssignment_AssignmentNotFound() {
        Long studentId = 1L;
        Long courseId = 2L;
        Long assignmentId = 3L;
        String assignmentAnswer = "Assignment answer";

        Course course = new Course();
        course.setId(courseId);

        Student student = new Student();
        student.setId(studentId);
        Set<Course> courses = new HashSet<>();
        courses.add(course);
        student.setCourses(courses);

        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));
        when(assignmentRepository.findById(assignmentId)).thenReturn(Optional.empty());

        assignmentService.submitAssignment(studentId, courseId, assignmentId, assignmentAnswer);
    }

    @Test(expectedExceptions = {StudentNotEnrolledToCourseException.class})
    public void testSubmitAssignment_StudentNotEnrolledInCourse() {
        Long studentId = 1L;
        Long courseId = 1L;
        Long assignmentId = 1L;
        String assignmentAnswer = "Assignment answer";

        Course course = new Course();
        course.setId(courseId);

        Student student = new Student();
        student.setId(studentId);

        Assignment assignment = new Assignment();
        assignment.setId(1L);

        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));
        when(assignmentRepository.findById(assignmentId)).thenReturn(Optional.of(assignment));

        assignmentService.submitAssignment(studentId, courseId, assignmentId, assignmentAnswer);
    }

    @Test(expectedExceptions = {AssignmentNotValidException.class})
    public void testSubmitAssignment_AssignmentNotValid() {
        Long studentId = 1L;
        Long courseId = 1L;
        Long assignmentId = 1L;
        String assignmentAnswer = ""; // Empty assignment answer

        Course course = new Course();
        course.setId(courseId);

        Student student = new Student();
        student.setId(studentId);
        Set<Course> courses = new HashSet<>();
        courses.add(course);
        student.setCourses(courses);
        Assignment assignment = new Assignment();
        assignment.setId(assignmentId);

        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));
        when(assignmentRepository.findById(assignmentId)).thenReturn(Optional.of(assignment));

        assignmentService.submitAssignment(studentId, courseId, assignmentId, assignmentAnswer);
    }
}
