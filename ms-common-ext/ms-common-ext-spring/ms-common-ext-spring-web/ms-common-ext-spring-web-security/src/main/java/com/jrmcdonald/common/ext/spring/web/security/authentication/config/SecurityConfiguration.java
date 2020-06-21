package com.jrmcdonald.common.ext.spring.web.security.authentication.config;

import com.jrmcdonald.common.ext.spring.web.security.authentication.AuthenticationFacade;
import com.jrmcdonald.common.ext.spring.web.security.authentication.SpringSecurityAuthenticationFacade;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecurityConfiguration {

    @Bean
    AuthenticationFacade authenticationFacade() {
        return new SpringSecurityAuthenticationFacade();
    }
}
