package com.example.Web.exception.handler;

import com.example.Web.dto.ErrorRes;
import com.example.Web.exception.CustomException;
import com.example.Web.exception.EmployeeUnableToUpdateException;
import com.example.Web.exception.EmployeesNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class EmployeeUnableToUpdateExceptionHandler  extends ResponseEntityExceptionHandler {
    @ExceptionHandler(EmployeeUnableToUpdateException.class)
    public ResponseEntity<ErrorRes> handleControllerNotFound(EmployeeUnableToUpdateException exception) {
        ErrorRes errorResponse = ErrorRes.builder()
                .errorMessage(exception.getMessage())
                .errorCode(exception.getErrorCode())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
