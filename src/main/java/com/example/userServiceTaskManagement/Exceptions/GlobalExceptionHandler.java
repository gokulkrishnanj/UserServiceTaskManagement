package com.example.userServiceTaskManagement.Exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> hadleInvalidArgumentException(MethodArgumentNotValidException exception) {
        Map<String, String> errorResponse = new HashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(fieldError -> errorResponse.put(fieldError.getField(), fieldError.getDefaultMessage()));
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TasksNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleTaskNotFoundException(TasksNotFoundException tasksNotFoundException) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("message", tasksNotFoundException.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<Map<String, String>> nullPointerException(NullPointerException nullPointerException) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("message", nullPointerException.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatusCode.valueOf(500));
    }
    // to handle sql exceptions
}
