package com.compassouol.productms.commons.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    void handle(ResourceNotFoundException e) {
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorView handle(MethodArgumentNotValidException e) {
        var bindingResult = e.getBindingResult();
        var errorView = new ErrorView();

        bindingResult.getFieldErrors()
                .forEach(f -> errorView.addError(HttpStatus.BAD_REQUEST.value(), f.getField(), f.getDefaultMessage()));

        return errorView;
    }

}
