package com.example.kalvi.service;

import com.example.kalvi.dto.AssignmentProgressDTO;
import com.example.kalvi.dto.QuizProgressDTO;
import com.example.kalvi.dto.StudentProgressDTO;
import com.example.kalvi.entity.StudentProgress;
import com.example.kalvi.repository.StudentProgressRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class StudentProgressService {
    @Autowired
    private StudentProgressRepository studentProgressRepository;

    public List<StudentProgress> getAllStudentProgress(Long studentId) {
        return studentProgressRepository.findByStudentId(studentId);
    }

    public static StudentProgressDTO convertToDTO(StudentProgress studentProgress) {
        StudentProgressDTO studentProgressDTO = new StudentProgressDTO();
        studentProgressDTO.setCourseId(studentProgress.getCourse().getId());

        studentProgress.getQuizProgresses().forEach(quizProgress -> {
            QuizProgressDTO quizProgressDTO = new QuizProgressDTO();
            quizProgressDTO.setQuizzId(quizProgress.getId());
            quizProgressDTO.setScore(quizProgress.getScore());
            quizProgressDTO.setQuizTitle(quizProgress.getQuiz().getTitle());

            studentProgressDTO.getQuizProgress().add(quizProgressDTO);
        });

        studentProgress.getAssignmentProgresses().forEach(assignmentProgress -> {
            AssignmentProgressDTO assignmentProgressDTO = new AssignmentProgressDTO();
            assignmentProgressDTO.setAssignmentId(assignmentProgress.getId());
            assignmentProgressDTO.setCompleted(assignmentProgress.isSubmitted());
            assignmentProgressDTO.setAssignmentTitle(assignmentProgress.getAssignment().getAssignmentTitle());
            studentProgressDTO.getAssingmentProgress().add(assignmentProgressDTO);
        });

        return studentProgressDTO;
    }
}