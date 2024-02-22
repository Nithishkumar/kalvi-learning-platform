package com.example.kalvi.entity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class StudentProgress {

    public StudentProgress() {
        // default constructor
    }
    public StudentProgress(Course course, Student student) {
        this.course = course;
        this.student = student;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

    @OneToMany(mappedBy = "studentProgress", cascade = CascadeType.ALL)
    private Set<QuizProgress> quizProgresses = new HashSet<>();

    @OneToMany(mappedBy = "studentProgress", cascade = CascadeType.ALL)
    private Set<AssignmentProgress> assignmentProgresses = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Set<QuizProgress> getQuizProgresses() {
        return quizProgresses;
    }

    public void setQuizProgresses(Set<QuizProgress> quizProgresses) {
        this.quizProgresses = quizProgresses;
    }

    public Set<AssignmentProgress> getAssignmentProgresses() {
        return assignmentProgresses;
    }

    public void setAssignmentProgresses(Set<AssignmentProgress> assignmentProgresses) {
        this.assignmentProgresses = assignmentProgresses;
    }
}
