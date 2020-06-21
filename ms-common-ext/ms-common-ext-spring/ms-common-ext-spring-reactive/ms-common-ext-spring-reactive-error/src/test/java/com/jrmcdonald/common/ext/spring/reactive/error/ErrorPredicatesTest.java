package com.jrmcdonald.common.ext.spring.reactive.error;

import com.jrmcdonald.common.schema.definition.exception.ConflictException;
import com.jrmcdonald.common.schema.definition.exception.NotFoundException;
import com.jrmcdonald.common.schema.definition.exception.ServiceException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.ClientResponse;

import reactor.test.StepVerifier;

import static com.jrmcdonald.common.ext.spring.reactive.error.ErrorPredicates.conflictException;
import static com.jrmcdonald.common.ext.spring.reactive.error.ErrorPredicates.notFoundException;
import static com.jrmcdonald.common.ext.spring.reactive.error.ErrorPredicates.serviceException;

@ExtendWith(MockitoExtension.class)
class ErrorPredicatesTest {

    @Mock
    ClientResponse clientResponse;

    @Test
    @DisplayName("Should return wrapped NotFoundException")
    void shouldReturnWrappedNotFoundException() {
        StepVerifier.create(notFoundException(clientResponse))
                    .expectError(NotFoundException.class)
                    .verify();
    }

    @Test
    @DisplayName("Should return wrapped ConflictException")
    void shouldReturnWrappedConflictException() {
        StepVerifier.create(conflictException(clientResponse))
                    .expectError(ConflictException.class)
                    .verify();
    }

    @Test
    @DisplayName("Should return wrapped ServiceException")
    void shouldReturnWrappedServiceException() {
        StepVerifier.create(serviceException(clientResponse))
                    .expectError(ServiceException.class)
                    .verify();
    }
}