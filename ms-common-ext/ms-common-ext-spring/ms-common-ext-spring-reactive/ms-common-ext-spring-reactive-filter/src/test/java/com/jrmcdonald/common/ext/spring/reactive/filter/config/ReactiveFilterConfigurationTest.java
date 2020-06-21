package com.jrmcdonald.common.ext.spring.reactive.filter.config;

import com.jrmcdonald.common.ext.spring.reactive.filter.logging.ReactiveRequestLoggingFilter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

import static org.assertj.core.api.Assertions.assertThat;

class ReactiveFilterConfigurationTest {

    private final ApplicationContextRunner runner = new ApplicationContextRunner()
            .withUserConfiguration(ReactiveFilterConfiguration.class)
            .withPropertyValues("spring.application.name", "ms-common-ext-spring-reactive-filter");

    @Test
    @DisplayName("Should create ReactiveRequestLoggingFilter bean")
    void shouldCreateReactiveRequestLoggingFilterBean() {
        runner.run(ctx -> assertThat(ctx.getBean("reactiveRequestLoggingFilter")).isInstanceOf(ReactiveRequestLoggingFilter.class));
    }
}