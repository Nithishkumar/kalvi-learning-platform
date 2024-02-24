package com.example.kalvi.entity;

import jakarta.persistence.Embeddable;

@Embeddable
public class Answer {
    public Answer() {
    }

    public Answer(String answer) {
        this.answer = answer;
    }

    private String answer;

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
