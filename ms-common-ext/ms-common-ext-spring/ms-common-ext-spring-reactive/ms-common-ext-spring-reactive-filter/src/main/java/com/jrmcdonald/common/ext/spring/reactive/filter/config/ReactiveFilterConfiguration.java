package com.jrmcdonald.common.ext.spring.reactive.filter.config;

import com.jrmcdonald.common.ext.spring.reactive.filter.logging.ReactiveRequestLoggingFilter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ReactiveFilterConfiguration {

    @Bean
    public ReactiveRequestLoggingFilter reactiveRequestLoggingFilter(@Value("${spring.application.name}") String applicationName) {
        return new ReactiveRequestLoggingFilter(applicationName);
    }
}
