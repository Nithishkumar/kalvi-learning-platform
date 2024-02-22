package com.example.kalvi.exception;

public class InvalidQuizSubmissionException extends RuntimeException {

    public InvalidQuizSubmissionException(String message) {
        super(message);
    }
}