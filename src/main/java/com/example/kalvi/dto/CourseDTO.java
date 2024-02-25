package com.example.kalvi.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CourseDTO {

    public CourseDTO(){
        this.modules = new ArrayList<>();
        this.languages = new ArrayList<>();
        this.assignments = new HashSet<>();
        this.quizzes = new HashSet<>();
        this.ratings = new ArrayList<>();
    }
    @JsonIgnore
    private Long id;

    private String courseName;
    private String description;
    private LocalDateTime lastUpdated;
    private LocalDateTime createdAt;
    private String createdBy;
    private List<RatingDTO> ratings;
    private List<String> languages;
    private double price;
    private List<ModuleDTO> modules;
    private Set<QuizDTO> quizzes;
    private Set<AssignmentDTO> assignments;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public List<RatingDTO> getRatings() {
        return ratings;
    }

    public void setRatings(List<RatingDTO> ratings) {
        this.ratings = ratings;
    }

    public List<String> getLanguages() {
        return languages;
    }

    public void setLanguages(List<String> languages) {
        this.languages = languages;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public List<ModuleDTO> getModules() {
        return modules;
    }

    public void setModules(List<ModuleDTO> modules) {
        this.modules = modules;
    }

    public Set<QuizDTO> getQuizzes() {
        return quizzes;
    }

    public void setQuizzes(Set<QuizDTO> quizzes) {
        this.quizzes = quizzes;
    }

    public Set<AssignmentDTO> getAssignments() {
        return assignments;
    }

    public void setAssignments(Set<AssignmentDTO> assignments) {
        this.assignments = assignments;
    }
}
