package com.jrmcdonald.common.ext.spring.reactive.security.authentication;

import org.springframework.security.core.Authentication;

import reactor.core.publisher.Mono;

public interface ReactiveAuthenticationFacade {

    Mono<Authentication> getAuthentication();

    Mono<String> getCustomerId();
}
