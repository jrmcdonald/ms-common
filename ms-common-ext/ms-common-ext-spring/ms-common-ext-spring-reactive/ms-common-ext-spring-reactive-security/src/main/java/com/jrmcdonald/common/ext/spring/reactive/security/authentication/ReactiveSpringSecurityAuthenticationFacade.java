package com.jrmcdonald.common.ext.spring.reactive.security.authentication;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;

import reactor.core.publisher.Mono;

public class ReactiveSpringSecurityAuthenticationFacade implements ReactiveAuthenticationFacade {

    @Override
    public Mono<Authentication> getAuthentication() {
        return ReactiveSecurityContextHolder.getContext()
                                            .map(SecurityContext::getAuthentication);
    }

    @Override
    public Mono<String> getCustomerId() {
        return getAuthentication().map(Authentication::getName);
    }
}
