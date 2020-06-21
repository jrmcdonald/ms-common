package com.jrmcdonald.common.ext.spring.reactive.security.authentication.config;

import com.jrmcdonald.common.ext.spring.reactive.security.authentication.ReactiveAuthenticationFacade;
import com.jrmcdonald.common.ext.spring.reactive.security.authentication.ReactiveSpringSecurityAuthenticationFacade;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ReactiveAuthenticationConfiguration {

    @Bean
    public ReactiveAuthenticationFacade reactiveAuthenticationFacade() {
        return new ReactiveSpringSecurityAuthenticationFacade();
    }

}
