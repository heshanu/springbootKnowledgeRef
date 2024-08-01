package com.example.Web.exception;


import lombok.Getter;

@Getter
public class EmployeesNotFoundException extends RuntimeException{
    private final String errorCode;

    public EmployeesNotFoundException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

}
