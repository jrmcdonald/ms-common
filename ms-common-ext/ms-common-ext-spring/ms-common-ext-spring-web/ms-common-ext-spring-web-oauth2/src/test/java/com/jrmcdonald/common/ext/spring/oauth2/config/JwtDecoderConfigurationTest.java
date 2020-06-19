package com.jrmcdonald.common.ext.spring.oauth2.config;

import com.jrmcdonald.common.ext.spring.datetime.config.DateTimeConfiguration;
import com.jrmcdonald.common.ext.spring.web.oauth2.config.JwtDecoderConfiguration;
import com.jrmcdonald.ext.spring.core.oauth2.JwtAudienceValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.autoconfigure.context.ConfigurationPropertiesAutoConfiguration;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtIssuerValidator;
import org.springframework.security.oauth2.jwt.JwtTimestampValidator;

import static org.assertj.core.api.Assertions.assertThat;

class JwtDecoderConfigurationTest {

    private final ApplicationContextRunner runner = new ApplicationContextRunner()
            .withConfiguration(AutoConfigurations.of(ConfigurationPropertiesAutoConfiguration.class))
            .withUserConfiguration(DateTimeConfiguration.class, JwtDecoderConfiguration.class);

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

    @Test
    @DisplayName("Should create JwtDecoder bean")
    void shouldCreateJwtDecoderBean() {
        runner.run(ctx -> assertThat(ctx.getBean("jwtDecoder")).isInstanceOf(JwtDecoder.class));
    }
}