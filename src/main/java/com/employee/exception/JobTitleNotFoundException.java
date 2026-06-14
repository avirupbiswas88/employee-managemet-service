package com.employee.exception;

public class JobTitleNotFoundException extends RuntimeException {

    public JobTitleNotFoundException(String message) {
        super(message);
    }
}
