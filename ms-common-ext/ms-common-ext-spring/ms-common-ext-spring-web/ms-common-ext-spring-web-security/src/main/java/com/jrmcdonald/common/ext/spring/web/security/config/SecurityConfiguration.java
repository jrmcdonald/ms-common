package com.jrmcdonald.common.ext.spring.web.security.config;

import com.jrmcdonald.common.ext.spring.web.security.AuthenticationFacade;
import com.jrmcdonald.common.ext.spring.web.security.SpringSecurityAuthenticationFacade;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecurityConfiguration {

    @Bean
    AuthenticationFacade authenticationFacade() {
        return new SpringSecurityAuthenticationFacade();
    }
}
