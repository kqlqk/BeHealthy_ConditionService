package me.kqlqk.behealthy.condition_service.exception;

import me.kqlqk.behealthy.condition_service.dto.ExceptionDTO;
import me.kqlqk.behealthy.condition_service.exception.exceptions.UserConditionAlreadyExistsException;
import me.kqlqk.behealthy.condition_service.exception.exceptions.UserConditionNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({UserConditionAlreadyExistsException.class, UserConditionNotFoundException.class, IllegalArgumentException.class, NullPointerException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionDTO handle(Exception e) {
        ExceptionDTO exceptionDTO = new ExceptionDTO();
        exceptionDTO.setInfo(e.getMessage());

        return exceptionDTO;
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionDTO handleHttpMessageNotReadableEx() {
        ExceptionDTO exceptionDTO = new ExceptionDTO();
        exceptionDTO.setInfo("Required request body is missing");

        return exceptionDTO;
    }
}
