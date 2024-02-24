package com.example.kalvi.exception;

public class StudentNotEnrolledToCourseException extends RuntimeException{
    public StudentNotEnrolledToCourseException(String message){
        super(message);
    }
}
