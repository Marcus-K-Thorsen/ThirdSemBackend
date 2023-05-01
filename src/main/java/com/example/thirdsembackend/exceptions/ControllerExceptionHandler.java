package com.example.thirdsembackend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class ControllerExceptionHandler {

  @ExceptionHandler(Exception.class)
  ResponseEntity<ErrorMessage> globalExceptionHandler(Exception ex, WebRequest request) {
    ErrorMessage errorMessage = new ErrorMessage(
        HttpStatus.INTERNAL_SERVER_ERROR.value(),
        new Date(),
        "Global Exception caught: " + ex.getMessage(),
        "The description of the request: " + request.getDescription(true));
    return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(ResourceNotFoundException.class)
  ResponseEntity<ErrorMessage> resourceNotFoundExceptionHandler(Exception ex, WebRequest request) {
    ErrorMessage errorMessage = new ErrorMessage(
        HttpStatus.NOT_ACCEPTABLE.value(),
        new Date(),
        "Resource Not Found Exception caught: " + ex.getMessage(),
        "The description of the request: " + request.getDescription(true));
    return new ResponseEntity<>(errorMessage, HttpStatus.NOT_ACCEPTABLE);
  }

  @ExceptionHandler(ResourceExistsAlreadyException.class)
  ResponseEntity<ErrorMessage> resourceAlreadyExistsExceptionHandler(Exception ex, WebRequest request) {
    ErrorMessage errorMessage = new ErrorMessage(
        HttpStatus.NOT_ACCEPTABLE.value(),
        new Date(),
        "Resource Already Exists Exception caught: " + ex.getMessage(),
        "The description of the request: " + request.getDescription(true));
    return new ResponseEntity<>(errorMessage, HttpStatus.NOT_ACCEPTABLE);
  }
}
