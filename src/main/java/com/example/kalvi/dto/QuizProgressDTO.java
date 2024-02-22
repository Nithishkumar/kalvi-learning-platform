package com.example.kalvi.dto;

public class QuizProgressDTO {
    private Long quizId;

    private String quizTitle;
    private int score;

    public Long getQuizzId() {
        return quizId;
    }

    public void setQuizzId(Long id) {
        this.quizId = id;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getQuizTitle() {
        return quizTitle;
    }

    public void setQuizTitle(String quizTitle) {
        this.quizTitle = quizTitle;
    }
}
