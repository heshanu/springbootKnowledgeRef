package com.example.Web.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Getter
public class EmployeeUnableToSaveException extends RuntimeException{

    private final String errorCode;
    public  EmployeeUnableToSaveException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

}
