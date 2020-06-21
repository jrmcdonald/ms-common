package com.jrmcdonald.common.ext.spring.oauth2.config;

import com.jrmcdonald.common.ext.spring.core.oauth2.config.JwtValidatorConfiguration;
import com.jrmcdonald.common.ext.spring.datetime.config.DateTimeConfiguration;
import com.jrmcdonald.common.ext.spring.web.oauth2.jwt.config.JwtDecoderConfiguration;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.autoconfigure.context.ConfigurationPropertiesAutoConfiguration;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.security.oauth2.jwt.JwtDecoder;

import static org.assertj.core.api.Assertions.assertThat;

class JwtDecoderConfigurationTest {

    private final ApplicationContextRunner runner = new ApplicationContextRunner()
            .withConfiguration(AutoConfigurations.of(ConfigurationPropertiesAutoConfiguration.class))
            .withUserConfiguration(DateTimeConfiguration.class, JwtValidatorConfiguration.class, JwtDecoderConfiguration.class);

    @Test
    @DisplayName("Should create JwtDecoder bean")
    void shouldCreateJwtDecoderBean() {
        runner.run(ctx -> assertThat(ctx.getBean("jwtDecoder")).isInstanceOf(JwtDecoder.class));
    }
}