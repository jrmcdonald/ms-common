package com.jrmcdonald.common.ext.spring.datetime.config;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

import java.time.Clock;

import static org.assertj.core.api.Assertions.assertThat;

class DateTimeConfigurationTest {

    private final ApplicationContextRunner runner = new ApplicationContextRunner()
            .withConfiguration(AutoConfigurations.of(DateTimeConfiguration.class));

    @Test
    @DisplayName("Should create Clock bean")
    void shouldCreateClockBean() {
        runner.run(ctx -> assertThat(ctx.getBean("clock")).isInstanceOf(Clock.class));
    }
}