package com.jrmcdonald.common.ext.spring.reactive.oauth2.client.credentials.config.app;


import com.jrmcdonald.common.ext.spring.reactive.oauth2.client.credentials.config.ReactiveClientCredentialsConfiguration;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@SpringBootConfiguration
@EnableWebFluxSecurity
@Import(ReactiveClientCredentialsConfiguration.class)
public class ReactiveClientCredentialsConfigurationApplicationConfiguration {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http.authorizeExchange()
            .anyExchange().permitAll()
            .and()
            .csrf().disable();

        return http.build();
    }
}
