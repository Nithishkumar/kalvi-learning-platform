package com.example.kalvi.exception;

public class StudentNotEnrolledException extends RuntimeException {
    public StudentNotEnrolledException(String message) {
        super(message);
    }
}
