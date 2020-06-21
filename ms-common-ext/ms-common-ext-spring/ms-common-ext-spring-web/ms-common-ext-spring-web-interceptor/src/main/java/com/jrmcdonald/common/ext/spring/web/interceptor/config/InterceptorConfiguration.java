package com.jrmcdonald.common.ext.spring.web.interceptor.config;

import com.jrmcdonald.common.ext.spring.web.interceptor.RequestLoggingInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@Configuration
public class InterceptorConfiguration {

    @Bean
    public RequestLoggingInterceptor requestLoggingInterceptor(Clock clock) {
        return new RequestLoggingInterceptor(clock);
    }

}