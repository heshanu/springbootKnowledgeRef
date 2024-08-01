package com.example.Web.exception;

import lombok.Getter;

@Getter
public class EmployeeUnableToUpdateException extends RuntimeException{
    private final String errorCode;

    public EmployeeUnableToUpdateException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }


}
