package com.kalvi.tests.service;

import com.example.kalvi.dto.StudentProgressDTO;
import com.example.kalvi.entity.Assignment;
import com.example.kalvi.entity.AssignmentProgress;
import com.example.kalvi.entity.Course;
import com.example.kalvi.entity.Quiz;
import com.example.kalvi.entity.QuizProgress;
import com.example.kalvi.entity.Student;
import com.example.kalvi.entity.StudentProgress;
import com.example.kalvi.repository.StudentProgressRepository;
import com.example.kalvi.service.StudentProgressService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

public class StudentProgressServiceTest {

    @Mock
    private StudentProgressRepository studentProgressRepository;

    @InjectMocks
    private StudentProgressService studentProgressService;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllStudentProgress() {
        Long studentId = 1L;
        List<StudentProgress> studentProgressList = new ArrayList<>();
        studentProgressList.add(createMockStudentProgress());

        when(studentProgressRepository.findByStudentId(studentId)).thenReturn(studentProgressList);

        List<StudentProgress> result = studentProgressService.getAllStudentProgress(studentId);

        assertEquals(studentProgressList, result);
    }

    @Test
    public void testConvertToDTO() {
        StudentProgress studentProgress = createMockStudentProgress();

        StudentProgressDTO studentProgressDTO = StudentProgressService.convertToDTO(studentProgress);

        assertEquals(studentProgress.getCourse().getId(), studentProgressDTO.getCourseId());
        assertEquals(1, studentProgressDTO.getQuizProgress().size());
        assertEquals(1, studentProgressDTO.getAssingmentProgress().size());
    }

    private StudentProgress createMockStudentProgress() {
        Student student = new Student();
        student.setId(1L);

        Course course = new Course();
        course.setId(1L);

        Quiz quiz = new Quiz();
        quiz.setId(1L);
        quiz.setTitle("Test Quiz");

        Assignment assignment = new Assignment();
        assignment.setId(1L);
        assignment.setAssignmentTitle("Test Assignment");

        StudentProgress studentProgress = new StudentProgress();
        studentProgress.setStudent(student);
        studentProgress.setCourse(course);

        QuizProgress quizProgress = new QuizProgress();
        quizProgress.setId(1L);
        quizProgress.setQuiz(quiz);
        quizProgress.setScore(100);
        studentProgress.getQuizProgresses().add(quizProgress);

        AssignmentProgress assignmentProgress = new AssignmentProgress();
        assignmentProgress.setId(1L);
        assignmentProgress.setAssignment(assignment);
        assignmentProgress.setSubmitted(true);
        studentProgress.getAssignmentProgresses().add(assignmentProgress);

        return studentProgress;
    }
}
