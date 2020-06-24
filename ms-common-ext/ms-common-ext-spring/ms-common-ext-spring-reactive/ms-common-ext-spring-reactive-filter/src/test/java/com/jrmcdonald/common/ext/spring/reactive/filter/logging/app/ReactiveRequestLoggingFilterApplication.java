package com.jrmcdonald.common.ext.spring.reactive.filter.logging.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.jrmcdonald.common.ext.spring.reactive.filter.logging.app")
public class ReactiveRequestLoggingFilterApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReactiveRequestLoggingFilterApplication.class, args);
    }

}
