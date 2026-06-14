package com.employee.exception;

public class MissingRequiredDataException extends RuntimeException{

    public MissingRequiredDataException(String message) {
        super(message);
    }
}
