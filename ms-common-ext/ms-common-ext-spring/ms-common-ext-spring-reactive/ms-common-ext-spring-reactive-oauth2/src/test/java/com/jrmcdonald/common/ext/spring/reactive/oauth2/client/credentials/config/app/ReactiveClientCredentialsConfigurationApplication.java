package com.jrmcdonald.common.ext.spring.reactive.oauth2.client.credentials.config.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.jrmcdonald.common.ext.spring.reactive.oauth2.client.credentials.config.app")
public class ReactiveClientCredentialsConfigurationApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReactiveClientCredentialsConfigurationApplication.class, args);
    }
}
