package com.jrmcdonald.common.ext.spring.web.exception.handler;

import com.jrmcdonald.common.schema.definition.exception.ConflictException;
import com.jrmcdonald.common.schema.definition.exception.NotFoundException;
import com.jrmcdonald.common.schema.definition.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
public class ServiceExceptionHandler extends ResponseEntityExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public void handleException(NotFoundException ex) {
        log.warn("Requested resource could not be found", ex);
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(ConflictException.class)
    public void handleException(ConflictException ex) {
        log.warn("A conflict occurred", ex);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ServiceException.class)
    public void handleException(ServiceException ex) {
        log.warn("A internal server error occurred", ex);
    }
}

