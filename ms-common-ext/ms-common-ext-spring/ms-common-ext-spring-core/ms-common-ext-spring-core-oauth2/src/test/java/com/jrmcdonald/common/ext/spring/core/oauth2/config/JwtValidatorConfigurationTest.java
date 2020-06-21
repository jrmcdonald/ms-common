package com.jrmcdonald.common.ext.spring.core.oauth2.config;

import com.jrmcdonald.common.ext.spring.core.oauth2.JwtAudienceValidator;
import com.jrmcdonald.common.ext.spring.datetime.config.DateTimeConfiguration;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.autoconfigure.context.ConfigurationPropertiesAutoConfiguration;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.security.oauth2.jwt.JwtIssuerValidator;
import org.springframework.security.oauth2.jwt.JwtTimestampValidator;

import static org.assertj.core.api.Assertions.assertThat;

class JwtValidatorConfigurationTest {

    private final ApplicationContextRunner runner = new ApplicationContextRunner()
            .withConfiguration(AutoConfigurations.of(ConfigurationPropertiesAutoConfiguration.class))
            .withUserConfiguration(DateTimeConfiguration.class, JwtValidatorConfiguration.class);

    @Test
    @DisplayName("Should create JwtTimestampValidator bean")
    void shouldCreateJwtTimestampValidatorBean() {
        runner.run(ctx -> assertThat(ctx.getBean("timestampValidator")).isInstanceOf(JwtTimestampValidator.class));
    }

    @Test
    @DisplayName("Should create JwtIssuerValidator bean")
    void shouldCreateJwtIssuerValidatorBean() {
        runner.run(ctx -> assertThat(ctx.getBean("issuerValidator")).isInstanceOf(JwtIssuerValidator.class));
    }

    @Test
    @DisplayName("Should create JwtAudienceValidator bean")
    void shouldCreateJwtAudienceValidatorBean() {
        runner.run(ctx -> assertThat(ctx.getBean("audienceValidator")).isInstanceOf(JwtAudienceValidator.class));
    }
}