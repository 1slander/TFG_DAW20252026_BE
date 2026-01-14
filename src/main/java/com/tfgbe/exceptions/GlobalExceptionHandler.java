package com.tfgbe.exceptions;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
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
}
