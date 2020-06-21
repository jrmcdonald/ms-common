package com.jrmcdonald.common.ext.spring.reactive.error;

import com.jrmcdonald.common.schema.definition.exception.ConflictException;
import com.jrmcdonald.common.schema.definition.exception.NotFoundException;
import com.jrmcdonald.common.schema.definition.exception.ServiceException;

import org.springframework.web.reactive.function.client.ClientResponse;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import reactor.core.publisher.Mono;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ErrorPredicates {

    public static Mono<NotFoundException> notFoundException(ClientResponse clientResponse) {
        return Mono.error(new NotFoundException());
    }

    public static Mono<ConflictException> conflictException(ClientResponse clientResponse) {
        return Mono.error(new ConflictException());
    }

    public static Mono<ServiceException> serviceException(ClientResponse clientResponse) {
        return Mono.error(new ServiceException());
    }
}
