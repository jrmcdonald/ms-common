package com.jrmcdonald.common.ext.spring.reactive.filter.config;

import com.jrmcdonald.common.ext.spring.reactive.filter.logging.ReactiveRequestLoggingFilter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@Configuration
@EnableConfigurationProperties(ReactiveFilterConfigurationProperties.class)
public class ReactiveFilterConfiguration {

    @Bean
    public ReactiveRequestLoggingFilter reactiveRequestLoggingFilter(@Value("${spring.application.name}") String applicationName,
                                                                     ReactiveFilterConfigurationProperties properties,
                                                                     Clock clock) {
        return new ReactiveRequestLoggingFilter(applicationName, properties.getExcludedPaths(), clock);
    }
}
