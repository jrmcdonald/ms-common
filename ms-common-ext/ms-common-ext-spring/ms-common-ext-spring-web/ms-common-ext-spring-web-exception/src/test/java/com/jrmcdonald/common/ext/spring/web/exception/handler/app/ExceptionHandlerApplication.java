package com.jrmcdonald.common.ext.spring.web.exception.handler.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(scanBasePackages = "com.jrmcdonald.common.ext.spring.web.exception.handler.app", exclude = SecurityAutoConfiguration.class)
public class ExceptionHandlerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExceptionHandlerApplication.class, args);
    }

}
