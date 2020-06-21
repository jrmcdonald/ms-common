package com.jrmcdonald.common.ext.spring.reactive.context.config;

import com.jrmcdonald.common.ext.spring.reactive.context.lifter.ReactiveContextLifter;

import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import reactor.core.publisher.Hooks;
import reactor.core.publisher.Operators;

@Configuration
public class ReactiveContextLifterConfiguration {

    private static final String MDC_CONTEXT_REACTOR_KEY = ReactiveContextLifterConfiguration.class.getName();

    @PostConstruct
    private void contextOperatorHook() {
        Hooks.onEachOperator(MDC_CONTEXT_REACTOR_KEY,
                             Operators.lift((scannable, coreSubscriber) -> new ReactiveContextLifter<>(coreSubscriber))
        );
    }

    @PreDestroy
    private void cleanupHook() {
        Hooks.resetOnEachOperator(MDC_CONTEXT_REACTOR_KEY);
    }
}

