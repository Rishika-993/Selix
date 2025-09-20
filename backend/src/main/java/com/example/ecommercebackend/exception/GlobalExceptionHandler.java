package com.example.ecommercebackend.exception;

import com.example.ecommercebackend.dto.APIExceptionResponse;
import com.example.ecommercebackend.exception.custom.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<APIExceptionResponse> handleAPIException(APIException e) {
        String message = e.getMessage();
        APIExceptionResponse apiExceptionResponse = new APIExceptionResponse(message, false);
        if (message.contains("already exist"))
            return new ResponseEntity<>(apiExceptionResponse, HttpStatus.CONFLICT);
        if (message.contains("authentication is required"))
            return new ResponseEntity<>(apiExceptionResponse, HttpStatus.UNAUTHORIZED);

        return new ResponseEntity<>(apiExceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> myMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        Map<String, String> response = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach(err -> {
            String fieldName = ((FieldError) err).getField();
            String message = err.getDefaultMessage();
            response.put(fieldName, message);
        });
        return new ResponseEntity<Map<String, String>>(response,
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<APIExceptionResponse> handleResourceNotFoundException(ResourceNotFoundException e) {
        return new ResponseEntity<>(new APIExceptionResponse(e.getMessage(), false), HttpStatus.NOT_FOUND);
    }

    /*@ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<APIExceptionResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return new ResponseEntity<>(new APIExceptionResponse(e.getMessage(), false), HttpStatus.BAD_REQUEST);
    }*/

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<APIExceptionResponse> handleRuntimeException(RuntimeException e) {
        return new ResponseEntity<>(new APIExceptionResponse(e.getMessage(), false), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
