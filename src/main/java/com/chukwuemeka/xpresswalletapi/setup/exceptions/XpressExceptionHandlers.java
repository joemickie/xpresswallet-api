package com.chukwuemeka.xpresswalletapi.setup.exceptions;

import com.chukwuemeka.xpresswalletapi.payloads.responses.ApiResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@RequiredArgsConstructor
public class XpressExceptionHandlers extends ResponseEntityExceptionHandler {
    @ExceptionHandler(XpressException.class)
    public ApiResponse<String> handleXpressException (XpressException xpressException){
        return new ApiResponse<>(xpressException.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ApiResponse<String> handleUsernameNotFoundException(UsernameNotFoundException exception){
        return new ApiResponse<>(
                exception.getMessage(),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(JsonProcessingException.class)
    public ApiResponse<String> handleJsonProcessingException(JsonProcessingException exception){
        return new ApiResponse<>(
                exception.getMessage(),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ApiResponse<String> handleConstraintViolationException(ConstraintViolationException exception){
        StringBuilder message = new StringBuilder();
        exception.getConstraintViolations().forEach(constraintViolation -> {
            if(exception.getConstraintViolations().size() == 1) {
                message.append(constraintViolation.getMessage());
            } else {
                message.append(constraintViolation.getMessage()).append(". ");
            }
        });
        return new ApiResponse<>(
                message.toString(),
                HttpStatus.BAD_REQUEST
        );
    }
}
