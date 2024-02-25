package com.example.kalvi.dto;


import com.example.kalvi.entity.Question;

import java.util.ArrayList;
import java.util.List;

public class QuizDTO {

    public QuizDTO(){
        this.questions = new ArrayList<>();
    }
    private Long id;
    private String title;
    private List<Question> questions;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
}
