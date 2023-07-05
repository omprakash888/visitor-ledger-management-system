package com.immutable.visitormanagement.exception;

import com.immutable.visitormanagement.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleMethodArgsNotValidException(MethodArgumentNotValidException exception) {
        Map<String, String> response = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach((error)->{
            String fieldName = ((FieldError)error).getField();
            String message = error.getDefaultMessage();
            response.put(fieldName,message);
        });
        return new ResponseEntity<Map<String,String>>(response, HttpStatus.BAD_REQUEST);
    }

//    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
//    public ResponseEntity<ApiResponse> handleSQLIntegrityException(SQLIntegrityConstraintViolationException exception) {
//        String ex = "Your Email is already registered,Please login with your email";
//        ApiResponse apiResponse = new ApiResponse(new Date(),ex,false);
//        return new ResponseEntity<>(apiResponse,HttpStatus.BAD_REQUEST);
//    }
//
//    @ExceptionHandler(ResourceNotFoundException.class)
//    public ResponseEntity<ApiResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException exception) {
//        ApiResponse apiResponse = new ApiResponse(new Date(), exception.getMessage(),false);
//        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
//    }
//
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<ApiResponse> handleGlobalException(Exception ex) {
//        ApiResponse apiResponse = new ApiResponse(new Date(), ex.getMessage(),false);
//        return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
//    }
//
//    @ExceptionHandler(NoSuchElementException.class)
//    public ResponseEntity<ApiResponse> handleGlobalException(NoSuchElementException exception){
//        ApiResponse apiResponse = new ApiResponse(new Date(), exception.getMessage(),false);
//        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
//    }
}
