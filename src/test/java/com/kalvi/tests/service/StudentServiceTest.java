package com.kalvi.tests.service;

import com.example.kalvi.entity.Assignment;
import com.example.kalvi.entity.Course;
import com.example.kalvi.entity.Quiz;
import com.example.kalvi.entity.Student;
import com.example.kalvi.exception.CourseNotFoundException;
import com.example.kalvi.exception.StudentAlreadyEnrolledException;
import com.example.kalvi.exception.StudentNotFoundException;
import com.example.kalvi.repository.CourseRepository;
import com.example.kalvi.repository.StudentProgressRepository;
import com.example.kalvi.repository.StudentRepository;
import com.example.kalvi.service.StudentService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertThrows;

public class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private StudentProgressRepository studentProgressRepository;

    @InjectMocks
    private StudentService studentService;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testEnrollForCourse_Success() {
        Long studentId = 1L;
        Long courseId = 1L;
        Student student = new Student();
        student.setId(studentId);
        Course course = new Course();
        course.setId(courseId);
        course.setQuizzes(new HashSet<>());
        course.setAssignments(new HashSet<>());

        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));

        studentService.enrollForCourse(studentId, courseId);

        assert student.getCourses().contains(course);
        verify(studentRepository).save(student);
    }

    @Test
    public void testEnrollForCourse_Success_With_Assignment_Quiz() {
        Long studentId = 1L;
        Long courseId = 1L;
        Student student = new Student();
        student.setId(studentId);
        Course course = new Course();
        course.setId(courseId);
        Quiz quiz = new Quiz();
        quiz.setId(1L);
        quiz.setCourse(course);
        quiz.setQuestions(new ArrayList<>());
        quiz.setTitle("Sample quiz");
        Set<Quiz> quizSet = new HashSet<>();
        quizSet.add(quiz);
        Assignment assignment = new Assignment();
        assignment.setAssignmentTitle("Sample assignment");
        assignment.setCourse(course);
        assignment.setId(1L);
        Set<Assignment> assignments = new HashSet<>();
        assignments.add(assignment);

        course.setQuizzes(quizSet);
        course.setAssignments(assignments);

        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));

        studentService.enrollForCourse(studentId, courseId);

        assert student.getCourses().contains(course);
        verify(studentRepository).save(student);
    }

    @Test
    public void testEnrollForCourse_StudentNotFound() {
        Long studentId = 1L;
        Long courseId = 1L;

        when(studentRepository.findById(studentId)).thenReturn(Optional.empty());

        assertThrows(StudentNotFoundException.class, () -> studentService.enrollForCourse(studentId, courseId));
    }

    @Test
    public void testEnrollForCourse_CourseNotFound() {
        Long studentId = 1L;
        Long courseId = 1L;
        Student student = new Student();
        student.setId(studentId);

        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));
        when(courseRepository.findById(courseId)).thenReturn(Optional.empty());

        assertThrows(CourseNotFoundException.class, () -> studentService.enrollForCourse(studentId, courseId));
    }

    @Test
    public void testEnrollForCourse_StudentAlreadyEnrolled() {
        Long studentId = 1L;
        Long courseId = 1L;
        Student student = new Student();
        student.setId(studentId);
        Course course = new Course();
        course.setId(courseId);
        course.setQuizzes(new HashSet<>());
        course.setAssignments(new HashSet<>());
        student.getCourses().add(course);

        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));

        assertThrows(StudentAlreadyEnrolledException.class, () -> studentService.enrollForCourse(studentId, courseId));
    }

    @Test
    public void createStudent_Sucess(){
        Student student = new Student();
        student.setId(1L);
        when(studentRepository.save(student)).thenReturn(student);
        assertEquals(studentService.createStudent(student),student);
;    }
}
