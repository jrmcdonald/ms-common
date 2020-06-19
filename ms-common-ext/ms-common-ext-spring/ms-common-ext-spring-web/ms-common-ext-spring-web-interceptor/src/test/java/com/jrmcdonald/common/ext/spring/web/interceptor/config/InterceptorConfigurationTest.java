package com.jrmcdonald.common.ext.spring.web.interceptor.config;

import com.jrmcdonald.common.ext.spring.datetime.config.DateTimeConfiguration;
import com.jrmcdonald.common.ext.spring.web.interceptor.RequestLoggingInterceptor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

import static org.assertj.core.api.Assertions.assertThat;

class InterceptorConfigurationTest {

    private final ApplicationContextRunner runner = new ApplicationContextRunner()
            .withConfiguration(AutoConfigurations.of(DateTimeConfiguration.class, InterceptorConfiguration.class));

    @Test
    @DisplayName("Should create RequestLoggingInterceptor bean")
    void shouldCreateServiceEntryInterceptorBean() {
        runner.run(ctx -> assertThat(ctx.getBean("requestLoggingInterceptor")).isInstanceOf(RequestLoggingInterceptor.class));
    }
}