package com.example.kalvi.service;

import com.example.kalvi.entity.*;
import com.example.kalvi.exception.*;
import com.example.kalvi.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private QuizProgressRepository quizProgressRepository;

    @Autowired
    private StudentProgressRepository studentProgressRepository;

    public void submitQuiz(Long studentId, Long courseId, Long quizId, List<Answer> answers) {
        // Retrieve course, student, and quiz entities from repositories
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseNotFoundException("Course not found with id: " + courseId));

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new StudentNotFoundException("Student not found with id: " + studentId));

        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new QuizNotFoundException("Quiz not found with id: " + quizId));

        // Validate the quiz
        List<Question> questions = quiz.getQuestions();
        if (answers.size() != questions.size()) {
            throw new InvalidQuizSubmissionException("Number of answers does not match number of questions");
        }

        // Calculate the score based on correct answer and update
        int score = 0;
        for (int i = 0; i < questions.size(); i++) {
            Question question = questions.get(i);
            Answer answer = answers.get(i);
            if (answer.getAnswer().equals(question.getCorrectAnswer())) {
                score++;
            }
        }

        // Update student's progress for the specific quiz
        // Create a new QuizProgress entity and persist it with the StudentProgress
        StudentProgress studentProgress = student.getProgress().stream()
                .filter(progress -> progress.getCourse().equals(course))
                .findFirst()
                .orElseThrow(() -> new StudentProgressNotFoundException("Student progress not found for course and quiz"));



        for(QuizProgress quizProgressAvailable : studentProgress.getQuizProgresses()){
            if(quizProgressAvailable.getId().equals(quizId)){
                quizProgressAvailable.setScore(score);
                studentProgressRepository.save(studentProgress);
                return;
            }
        }
        // if the quiz progress is not available
        QuizProgress quizProgress = new QuizProgress();
        quizProgress.setStudentProgress(studentProgress);
        quizProgress.setQuiz(quiz);
        quizProgress.setScore(score);
        studentProgress.getQuizProgresses().add(quizProgress);
        studentProgressRepository.save(studentProgress);
    }
}
