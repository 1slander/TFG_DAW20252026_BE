package com.tfgbe.exceptions;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorMessage>handleNotFoundException(NotFoundException ex,WebRequest req){
        ErrorMessage errorMsg = new ErrorMessage(ex.getMessage(), HttpStatus.NOT_FOUND.value(), req.getDescription(false), LocalDateTime.now());
        return new ResponseEntity<>(errorMsg,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AlreadyExistsException.class)
public ResponseEntity<ErrorMessage> handleAlreadyExistException(AlreadyExistsException ex, WebRequest req) {
    ErrorMessage errorMsg = new ErrorMessage(
        ex.getMessage(), 
        HttpStatus.CONFLICT.value(),
        req.getDescription(false), 
        LocalDateTime.now()
    );
    return new ResponseEntity<>(errorMsg, HttpStatus.CONFLICT);
}

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorMessage> handleUnauthorizedException(UnauthorizedException ex, WebRequest req){
        ErrorMessage errMsg = new ErrorMessage(ex.getMessage(), HttpStatus.UNAUTHORIZED.value(),    req.getDescription(false) ,LocalDateTime.now() );
        //return new ResponseEntity<>(errMsg,HttpStatus.UNAUTHORIZED);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errMsg);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> handleValidationExceptions(MethodArgumentNotValidException e){
        
        Map<String,String> errors=new HashMap<>();

        e.getBindingResult().getFieldErrors().forEach(error ->
            errors.put(error.getField(),error.getDefaultMessage())
        );

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
