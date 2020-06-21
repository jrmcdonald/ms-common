package com.jrmcdonald.common.ext.spring.reactive.oauth2.jwt.config;

import com.jrmcdonald.common.ext.spring.core.oauth2.config.JwtValidatorConfiguration;
import com.jrmcdonald.common.ext.spring.datetime.config.DateTimeConfiguration;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.autoconfigure.context.ConfigurationPropertiesAutoConfiguration;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;

import static org.assertj.core.api.Assertions.assertThat;

class ReactiveJwtDecoderConfigurationTest {

    private final ApplicationContextRunner runner = new ApplicationContextRunner()
            .withConfiguration(AutoConfigurations.of(ConfigurationPropertiesAutoConfiguration.class))
            .withUserConfiguration(DateTimeConfiguration.class, JwtValidatorConfiguration.class, ReactiveJwtDecoderConfiguration.class);

    @Test
    @DisplayName("Should create ReactiveJwtDecoder bean")
    void shouldCreateReactiveJwtDecoderBean() {
        runner.run(ctx -> assertThat(ctx.getBean("reactiveJwtDecoder")).isInstanceOf(ReactiveJwtDecoder.class));
    }
}