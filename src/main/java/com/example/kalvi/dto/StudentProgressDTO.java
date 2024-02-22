package com.example.kalvi.dto;

import com.example.kalvi.entity.AssignmentProgress;
import com.example.kalvi.entity.QuizProgress;

import java.util.ArrayList;
import java.util.List;

public class StudentProgressDTO {
    public StudentProgressDTO() {
        this.assingmentProgress = new ArrayList<>();
        this.quizProgress = new ArrayList<>();
    }

    private Long courseId;
    private List<QuizProgressDTO> quizProgress;
    private List<AssignmentProgressDTO> assingmentProgress;

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public List<QuizProgressDTO> getQuizProgress() {
        return quizProgress;
    }

    public void setQuizProgress(List<QuizProgressDTO> quizProgress) {
        this.quizProgress = quizProgress;
    }

    public List<AssignmentProgressDTO> getAssingmentProgress() {
        return assingmentProgress;
    }

    public void setAssingmentProgress(List<AssignmentProgressDTO> assingmentProgress) {
        this.assingmentProgress = assingmentProgress;
    }
}
