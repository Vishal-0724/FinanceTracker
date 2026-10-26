package com.finance.financetracker.exception;

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

    //transaction already exists
    @ExceptionHandler(TransactionAlreadyExistsException.class)
    public ResponseEntity<?> handleTransactionAlreadyExists(TransactionAlreadyExistsException transactionAlreadyExistsException){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(transactionAlreadyExistsException.getMessage());
    }

    //transaction not exists
    @ExceptionHandler(TransactionNotExistsException.class)
    public ResponseEntity<?> handleTransactionNotFound(TransactionNotExistsException transactionNotExistsException){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(transactionNotExistsException.getMessage());
    }

    //handle validations
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidations(MethodArgumentNotValidException methodArgumentNotValidException){
        Map<String,String> errors = new HashMap<>();
        for(FieldError error : methodArgumentNotValidException.getFieldErrors()){
            errors.put(error.getField(),error.getDefaultMessage());
        }
        return new ResponseEntity<>(errors,HttpStatus.BAD_REQUEST);
    }

    //handle illegal arguments
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArguments(IllegalArgumentException illegalArgumentException){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(illegalArgumentException.getMessage());
    }

}
