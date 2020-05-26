package com.jrmcdonald.ext.spring.security.config;

import com.jrmcdonald.ext.spring.config.DateTimeConfiguration;
import com.jrmcdonald.ext.spring.security.SpringSecurityAuthenticationFacade;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

import static org.assertj.core.api.Assertions.assertThat;

class SecurityConfigurationTest {
    private final ApplicationContextRunner runner = new ApplicationContextRunner()
            .withConfiguration(AutoConfigurations.of(DateTimeConfiguration.class, SecurityConfiguration.class));

    @Test
    @DisplayName("Should create AuthenticationFace bean")
    void shouldCreateServiceEntryInterceptorBean() {
        runner.run(ctx -> assertThat(ctx.getBean("authenticationFacade")).isInstanceOf(SpringSecurityAuthenticationFacade.class));
    }
}