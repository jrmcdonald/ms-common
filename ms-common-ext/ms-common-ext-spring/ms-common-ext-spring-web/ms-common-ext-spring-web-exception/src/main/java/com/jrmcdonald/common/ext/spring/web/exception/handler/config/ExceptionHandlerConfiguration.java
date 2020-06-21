package com.jrmcdonald.common.ext.spring.web.exception.handler.config;

import com.jrmcdonald.common.ext.spring.web.exception.handler.ServiceExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExceptionHandlerConfiguration {

    @Bean
    public ServiceExceptionHandler customerExceptionHandler() { return new ServiceExceptionHandler(); }
}
