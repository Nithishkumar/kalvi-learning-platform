package com.kalvi.tests.service;

import com.example.kalvi.entity.*;
import com.example.kalvi.exception.*;
import com.example.kalvi.repository.*;
import com.example.kalvi.service.QuizService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.*;

import static org.mockito.Mockito.*;
import static org.testng.Assert.*;

public class QuizServiceTest {

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private QuizRepository quizRepository;

    @Mock
    private QuizProgressRepository quizProgressRepository;

    @Mock
    private StudentProgressRepository studentProgressRepository;

    @InjectMocks
    private QuizService quizService;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSubmitQuiz() {
        // Mock data
        Long studentId = 1L;
        Long courseId = 1L;
        Long quizId = 1L;
        List<Answer> answers = new ArrayList<>();
        answers.add(new Answer("Option1"));
        answers.add(new Answer("OptionB"));

        Course course = new Course();
        course.setId(courseId);

        Student student = new Student();
        student.getCourses().add(course);
        student.setId(studentId);
        student.setProgress(new HashSet<>());

        Quiz quiz = new Quiz();
        quiz.setId(quizId);
        List<Question> questions = new ArrayList<>();
        Question question1 = new Question();
        question1.setQuestionText("Question1");
        question1.setOptions(List.of("Option1", "Option2", "Option3"));
        question1.setCorrectAnswer("Option1");
        Question question2 = new Question();
        question2.setQuestionText("Question2");
        question2.setOptions(List.of("OptionA", "OptionB", "OptionC"));
        question2.setCorrectAnswer("OptionB");
        questions.add(question1);
        questions.add(question2);
        quiz.setQuestions(questions);

        StudentProgress studentProgress = new StudentProgress();
        studentProgress.setId(1L);
        studentProgress.setCourse(course);
        studentProgress.setStudent(student);
        Set<StudentProgress> studentProgressSet = new HashSet<>();
        studentProgressSet.add(studentProgress);
        student.setProgress(studentProgressSet);

        QuizProgress quizProgress = new QuizProgress();
        quizProgress.setId(quizId);
        quizProgress.setStudentProgress(studentProgress);

        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));
        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));
        when(quizRepository.findById(quizId)).thenReturn(Optional.of(quiz));
        when(studentProgressRepository.save(any())).thenReturn(studentProgress);

        // Test
        quizService.submitQuiz(studentId, courseId, quizId, answers);

        // Verify
        verify(studentProgressRepository, times(1)).save(studentProgress);
        assertEquals(studentProgress.getQuizProgresses().size(), 1);
        for(QuizProgress quizProgress1: studentProgress.getQuizProgresses()){
            assertEquals(quizProgress1.getScore(), 2); // Both answers are correct
        }
    }

    @Test(description = "It test the submit quiz with quiz progress")
    public void testSubmitQuizWithProgress() {
        // Mock data
        Long studentId = 1L;
        Long courseId = 1L;
        Long quizId = 1L;
        List<Answer> answers = new ArrayList<>();
        answers.add(new Answer("Option1"));
        answers.add(new Answer("OptionB"));

        Course course = new Course();
        course.setId(courseId);

        Student student = new Student();
        student.getCourses().add(course);
        student.setId(studentId);
        student.setProgress(new HashSet<>());

        Quiz quiz = new Quiz();
        quiz.setId(quizId);
        List<Question> questions = new ArrayList<>();
        Question question1 = new Question();
        question1.setQuestionText("Question1");
        question1.setOptions(List.of("Option1", "Option2", "Option3"));
        question1.setCorrectAnswer("Option1");
        Question question2 = new Question();
        question2.setQuestionText("Question2");
        question2.setOptions(List.of("OptionA", "OptionB", "OptionC"));
        question2.setCorrectAnswer("OptionB");
        questions.add(question1);
        questions.add(question2);
        quiz.setQuestions(questions);

        StudentProgress studentProgress = new StudentProgress();
        studentProgress.setId(1L);
        studentProgress.setCourse(course);
        studentProgress.setStudent(student);
        Set<StudentProgress> studentProgressSet = new HashSet<>();
        Set<QuizProgress> quizProgressSet = new HashSet<>();
        QuizProgress quizProgress = new QuizProgress();
        quizProgress.setId(quizId);
        quizProgress.setStudentProgress(studentProgress);
        quizProgressSet.add(quizProgress);
        studentProgress.setQuizProgresses(quizProgressSet);
        studentProgressSet.add(studentProgress);
        student.setProgress(studentProgressSet);



        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));
        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));
        when(quizRepository.findById(quizId)).thenReturn(Optional.of(quiz));
        when(studentProgressRepository.save(any())).thenReturn(studentProgress);

        // Test
        quizService.submitQuiz(studentId, courseId, quizId, answers);

        // Verify
        verify(studentProgressRepository, times(1)).save(studentProgress);
        assertEquals(studentProgress.getQuizProgresses().size(), 1);
        for(QuizProgress quizProgress1: studentProgress.getQuizProgresses()){
            assertEquals(quizProgress1.getScore(), 2); // Both answers are correct
        }
    }

    @Test(expectedExceptions = {CourseNotFoundException.class})
    public void testSubmitQuiz_courseNotFound() {
        Long studentId = 1L;
        Long courseId = 2L;
        Long quizId = 3L;
        List<Answer> answers = new ArrayList<>();
        answers.add(new Answer("Option1"));
        answers.add(new Answer("OptionB"));

        when(courseRepository.findById(courseId)).thenReturn(Optional.empty());

        quizService.submitQuiz(studentId, courseId, quizId, answers);
    }

    @Test(expectedExceptions = {StudentNotFoundException.class})
    public void testSubmitQuiz_studentNotFound() {
        Long studentId = 1L;
        Long courseId = 2L;
        Long quizId = 3L;
        List<Answer> answers = new ArrayList<>();
        answers.add(new Answer("Option1"));
        answers.add(new Answer("OptionB"));

        when(courseRepository.findById(courseId)).thenReturn(Optional.of(new Course()));
        when(studentRepository.findById(studentId)).thenReturn(Optional.empty());

        quizService.submitQuiz(studentId, courseId, quizId, answers);
    }

    @Test(expectedExceptions = {QuizNotFoundException.class})
    public void testSubmitQuiz_quizNotFound() {
        Long studentId = 1L;
        Long courseId = 2L;
        Long quizId = 3L;
        List<Answer> answers = new ArrayList<>();
        answers.add(new Answer("Option1"));
        answers.add(new Answer("OptionB"));

        when(courseRepository.findById(courseId)).thenReturn(Optional.of(new Course()));
        when(studentRepository.findById(studentId)).thenReturn(Optional.of(new Student()));
        when(quizRepository.findById(quizId)).thenReturn(Optional.empty());

        quizService.submitQuiz(studentId, courseId, quizId, answers);
    }

    @Test(expectedExceptions = {InvalidQuizSubmissionException.class})
    public void testSubmitQuiz_invalidQuizSubmission() {
        Long studentId = 1L;
        Long courseId = 2L;
        Long quizId = 3L;
        List<Answer> answers = new ArrayList<>();
        answers.add(new Answer("Option1"));

        when(courseRepository.findById(courseId)).thenReturn(Optional.of(new Course()));
        when(studentRepository.findById(studentId)).thenReturn(Optional.of(new Student()));
        when(quizRepository.findById(quizId)).thenReturn(Optional.of(new Quiz()));

        quizService.submitQuiz(studentId, courseId, quizId, answers);
    }
}
