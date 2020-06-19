package com.jrmcdonald.common.ext.spring.web.exception.handler.app;

import com.jrmcdonald.common.schema.definition.exception.ConflictException;
import com.jrmcdonald.common.schema.definition.exception.NotFoundException;
import com.jrmcdonald.common.schema.definition.exception.ServiceException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.jrmcdonald.common.ext.spring.web.exception.handler.app.ExceptionHandlerController.ENDPOINT;

@RestController
@RequestMapping(ENDPOINT)
public class ExceptionHandlerController {

    public static final String ENDPOINT = "/v1/exception";

    @PostMapping(path = "/not-found", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void postWithNotFoundException() {
        throw new NotFoundException();
    }

    @PostMapping(path = "/already-exists", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void postWithConflictException() {
        throw new ConflictException();
    }

    @PostMapping(path = "/server-error", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void postWithServiceException() {
        throw new ServiceException();
    }
}
